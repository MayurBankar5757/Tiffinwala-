package com.Tiffinwala.TiffinwalaAuthService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.Tiffinwala.TiffinwalaAuthService.Dummy.LoginCheck;
import com.Tiffinwala.TiffinwalaAuthService.Dummy.UserDummy;
import com.Tiffinwala.TiffinwalaAuthService.Entities.User;
import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaAuthService.Security.JwtUtil;
import com.Tiffinwala.TiffinwalaAuthService.Security.AuthenticationResponse;
import com.Tiffinwala.TiffinwalaAuthService.Security.CustomUserDetailsService;
import com.Tiffinwala.TiffinwalaAuthService.Services.UserService;


@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3010")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCheck login) {
        try {
        	System.out.println("Inside login");
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPwd())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

//    // Get a user by ID
//    @GetMapping("/{uid}")
//    public ResponseEntity<?> getUserById(@PathVariable Integer uid) {
//        try {
//            User user = userService.getUserById(uid);
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Get all customers for admin
//    @GetMapping("/getAllCustomers")
//    public ResponseEntity<?> getAllCustomers() {
//        try {
//        	System.out.println("GetAllCustomers");
//            List<User> users = userService.getAllCustomers();
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Update a user
//    @PutMapping("/{uid}")
//    public ResponseEntity<?> updateUser(@PathVariable Integer uid, @RequestBody UserDummy userRequest) {
//        try {
//            User updatedUser = userService.updateUserDetails(uid, userRequest);
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (DataIntegrityViolationException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update user. Try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Delete a user
//    @DeleteMapping("/{uid}")
//    public ResponseEntity<?> deleteUser(@PathVariable Integer uid) {
//        try {
//            userService.deleteUser(uid);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}

// Authentication Response Class
//class AuthenticationResponse {
//    private final String jwt;
//
//    public AuthenticationResponse(String jwt) {
//        this.jwt = jwt;
//    }
//
//    public String getJwt() {
//        return jwt;
//    }
//}
   }
