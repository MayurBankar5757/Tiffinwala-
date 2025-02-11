package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.LoginCheck;
import com.Tiffinwala.TiffinwalaCrudService.Dummy.UserDummy;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Services.UserService;

@RestController
@RequestMapping("/api3")
//@CrossOrigin(origins = "http://localhost:3010")
public class UserController {

    @Autowired
    private UserService userService;
    
  

    // Get a user by ID
    @GetMapping("/user/{uid}")
    public ResponseEntity<?> getUserById(@PathVariable Integer uid) {
        try {
            User user = userService.getUserById(uid);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all customers for admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/getAllCustomers")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<User> users = userService.getAllCustomers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a user
    @PutMapping("/user/{uid}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'CUSTOMER', 'ADMIN')") 
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user/{uid}")
    
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
