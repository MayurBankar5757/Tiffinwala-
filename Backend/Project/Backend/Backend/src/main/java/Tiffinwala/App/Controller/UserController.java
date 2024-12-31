package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.LoginCheck;
import Tiffinwala.App.Dummy.UserDummy;
import Tiffinwala.App.Entities.Address;
import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Services.RoleServices;
import Tiffinwala.App.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins ="http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleServices roleserv;

    // Create a new user
    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody UserDummy user) {
        // Get the role by roleid
        Role role = roleserv.getRoleById(user.getRoleid());
        
        // Initialize the Address object
        Address add = new Address(); 
        add.setArea(user.getArea());
        add.setCity(user.getCity());
        add.setPincode(user.getPincode());
        add.setState(user.getState());
        
        // Initialize the User object
        User realUser = new User();  
        
        // Ensure role and address are not null
        if (role != null && add != null) {
            // Set role and address
            realUser.setRole(role);
            realUser.setAddress(add);
            
            // Set other user details
            realUser.setFname(user.getFname());
            realUser.setLname(user.getLname());
            realUser.setPassword(user.getPassword());  // Make sure to hash the password before saving
            realUser.setContact(user.getContact());
            realUser.setEmail(user.getEmail());
            
            // Save the user to the database
            User savedUser = userService.createUser(realUser); // Assuming createUser() saves to the DB
            
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);  // Return the saved user
        } else {
            // Handle the error if role or address is not found
            System.out.println("Role or address not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return a bad request status
        }
    }
    
    // Login
    @PostMapping("/chkLogin")
    public User getLogin(@RequestBody LoginCheck login){
    	
    	
    	return userService.getLogin(login.getEmail(), login.getPwd());
    }

    // Get a user by ID
    @GetMapping("/{uid}")
    public ResponseEntity<User> getUserById(@PathVariable Integer uid) {
        return new ResponseEntity<>(userService.getUserById(uid), HttpStatus.OK);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // Update a user
    @PutMapping("/{uid}")
    public ResponseEntity<User> updateUser(@PathVariable Integer uid, @RequestBody UserDummy user) {
        // Fetch the existing user by UID
        User existingUser = userService.getUserById(uid);  // Assuming getUserById() retrieves the user by UID
        
        // Check if user exists
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // If user doesn't exist, return 404 Not Found
        }
        
        // Fetch the role by roleid
        Role role = roleserv.getRoleById(user.getRoleid());  // Get the role based on role ID
        
        // Update the address fields
        Address add = existingUser.getAddress();  // Get the existing address object
        if (add == null) {
            add = new Address();  // If the address doesn't exist, create a new one
        }
        add.setArea(user.getArea());
        add.setCity(user.getCity());
        add.setPincode(user.getPincode());
        add.setState(user.getState());
        
        // Update the existing user details
        existingUser.setFname(user.getFname());
        existingUser.setLname(user.getLname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());  // Make sure to hash the password before saving
        existingUser.setContact(user.getContact());
        
        // Update the user's role and address
        if (role != null) {
            existingUser.setRole(role);
        }
        existingUser.setAddress(add);
        
        // Save the updated user to the database
        User updatedUser = userService.updateUser(uid, existingUser);  // Assuming updateUser() handles updating the user in the DB
        
        // Return the updated user with status OK
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user--ok
    @DeleteMapping("/{uid}") 
    public ResponseEntity<Void> deleteUser(@PathVariable Integer uid) {
        userService.deleteUser(uid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get user by email--ok

        // Endpoint to find user by email
        @PostMapping("/findByEmail")
        public ResponseEntity<User> findByEmail(@RequestBody Map<String, String> requestBody) {
            String email = requestBody.get("email");
            
            if (email == null || email.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            try {
                User user = userService.getByEmail(email);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }


