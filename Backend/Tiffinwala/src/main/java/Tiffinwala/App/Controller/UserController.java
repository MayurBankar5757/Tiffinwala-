package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.LoginCheck;
import Tiffinwala.App.Dummy.UserDummy;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    // Login endpoint
    @PostMapping("/chkLogin")
    public ResponseEntity<?> login(@RequestBody LoginCheck login) {
        try {
            User user = userService.getLogin(login.getEmail(), login.getPwd());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Get a user by ID
    @GetMapping("/{uid}")
    public ResponseEntity<?> getUserById(@PathVariable Integer uid) {
        try {
            User user = userService.getUserById(uid);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all customers for admin
    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<User> users = userService.getAllCustomers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a user
    @PutMapping("/{uid}")
    public ResponseEntity<?> updateUser(@PathVariable Integer uid, @RequestBody UserDummy userRequest) {
        try {
            User updatedUser = userService.updateUserDetails(uid, userRequest);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user. Try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a user
    @DeleteMapping("/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer uid) {
        try {
            userService.deleteUser(uid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
