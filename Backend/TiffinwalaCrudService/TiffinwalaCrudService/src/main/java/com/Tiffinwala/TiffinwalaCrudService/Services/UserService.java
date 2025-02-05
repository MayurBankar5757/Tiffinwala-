package com.Tiffinwala.TiffinwalaCrudService.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.UserDummy;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Address;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Role;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Repository.RoleRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
  

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
        return userRepository.getAllCustomers(role);
    }

    // Update user details (business logic and validation)
    public User updateUserDetails(Integer uid, UserDummy userRequest) {
        // Validate role id
        if (userRequest.getRoleId() == null) {
            throw new IllegalArgumentException("Role id is required");
        }
        
        // Fetch existing user
        User existingUser = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
        
        // If an email change is attempted, disallow it
        if (!existingUser.getEmail().equals(userRequest.getEmail())) {
            throw new DataIntegrityViolationException("Email cannot be updated because it is unique.");
        }
        
        // Fetch the role using the role id from the DTO
        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + userRequest.getRoleId()));
        
        // Update user details (email remains unchanged)
        existingUser.setFname(userRequest.getFname());
        existingUser.setLname(userRequest.getLname());
        // Do not update email: existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword()); // Ensure you hash the password as needed
        existingUser.setContact(userRequest.getContact());
        existingUser.setRole(role);
        
        // Update or create address fields
        Address address = existingUser.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setArea(userRequest.getArea());
        address.setCity(userRequest.getCity());
        address.setPincode(userRequest.getPincode());
        address.setState(userRequest.getState());
        existingUser.setAddress(address);
        
        return userRepository.save(existingUser);
    }


    // Delete a user
    public void deleteUser(Integer uid) {
        User existingUser = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
        userRepository.delete(existingUser);
    }

    // Get user by email
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    // Login method
    public User getLogin(String email, String pwd) {
        Optional<User> userOpt = userRepository.getLogin(email, pwd);
        return userOpt.orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
    }

    // Get user by role
    public User getByRole(Integer rid) {
        Role role = roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + rid));
        return userRepository.getByRole(role);
    }
}
