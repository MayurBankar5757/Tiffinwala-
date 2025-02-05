package com.Tiffinwala.TiffinwalaAuthService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaAuthService.Dummy.UserDummy;
import com.Tiffinwala.TiffinwalaAuthService.Entities.Role;
import com.Tiffinwala.TiffinwalaAuthService.Entities.User;
import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ConflictException;
import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaAuthService.Repository.RoleRepository;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private com.Tiffinwala.TiffinwalaAuthService.Repository.UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create a new user
    public User createUser(User user) {
        try {
            Role role = roleRepository.findById(user.getRole().getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + user.getRole().getRoleId()));

            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Duplicate entry detected. Please ensure all fields are unique.");
        }
    }

    // Get a user by ID
    public User getUserById(Integer uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + uid));
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all customers
    public List<User> getAllCustomers() {
        Integer rid = 3; // Assuming role id 3 represents "customer"
        Role role = roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + rid));
        return userRepository.getAllCustomers(role); // Assuming you have this method in UserRepository
    }

    // Update user details
    public User updateUserDetails(Integer uid, UserDummy userRequest) {
        if (userRequest.getRoleId() == null) {
            throw new IllegalArgumentException("Role id is required");
        }

        User existingUser = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + uid));

        if (!existingUser.getEmail().equals(userRequest.getEmail())) {
            throw new ConflictException("Email cannot be updated because it is unique.");
        }

        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + userRequest.getRoleId()));

        existingUser.setFname(userRequest.getFname());
        existingUser.setLname(userRequest.getLname());
        existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Ensure password hashing
        existingUser.setContact(userRequest.getContact());
        existingUser.setRole(role);

        return userRepository.save(existingUser);
    }

    // Delete a user
    public void deleteUser(Integer uid) {
        User existingUser = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + uid));
        userRepository.delete(existingUser);
    }

    // Get user by email
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    // Login method
    public User getLogin(String email, String pwd) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(pwd, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        return user;
    }

    // Get user by role
    public User getByRole(Integer rid) {
        Role role = roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + rid));
        return userRepository.getByRole(role);
    }
}
