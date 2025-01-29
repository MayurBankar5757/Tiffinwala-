package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Repository.RoleRepository;
import Tiffinwala.App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    

    // Create a new user
    public User createUser(User user) {
        Role role = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + user.getRole().getRoleId()));
        user.setRole(role);
        return userRepository.save(user);
    }

    // Get a user by ID
    public User getUserById1(Integer uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + uid));
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all Customers
    public List<User> getAllCustomers() {
    	Integer rid = 3;
    	Role r = roleRepository.findById(rid).get();
		
        return userRepository.getAllCustomers(r);
    }
    
    
    public User getUserById(Integer uid) {
        return userRepository.findById(uid).orElse(null);  // Returns null if the user is not found
    }

   

    public User updateUser(Integer uid, User user) {
        if (!userRepository.existsById(uid)) {
            throw new ResourceNotFoundException("User not found with id " + uid);  // Throw an exception if user does not exist
        }
        user.setUid(uid);  // Ensure the UID is set before saving
        return userRepository.save(user);  // Save and return the updated user
    }


    // Delete a user
    public void deleteUser(Integer uid) {
        User existingUser = getUserById1(uid);
        userRepository.delete(existingUser);
    }

    // Get a user by email
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

	public User getLogin(String email, String pwd) {
		
	      User l;
	      Optional<User> u = userRepository.getLogin(email, pwd);
	      
	    
	      
	      try {
	    	  l = u.get();
	    	  
	      }
	      catch(Exception e) {
	    	  l = null;
	      }
	      return l;
	}
	
	public User getByRole(Integer rid) {
	
		Role r = roleRepository.findById(rid).get();
		return userRepository.getByRole(r);
	}
}
