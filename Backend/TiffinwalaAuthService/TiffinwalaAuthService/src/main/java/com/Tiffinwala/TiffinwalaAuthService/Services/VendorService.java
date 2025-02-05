package com.Tiffinwala.TiffinwalaAuthService.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.Tiffinwala.TiffinwalaAuthService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaAuthService.Entities.User;
import com.Tiffinwala.TiffinwalaAuthService.Entities.Role;
import com.Tiffinwala.TiffinwalaAuthService.Dummy.RegDummy;
import com.Tiffinwala.TiffinwalaAuthService.Entities.Address;
import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ConflictException;
import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaAuthService.Repository.UserRepository;
import com.Tiffinwala.TiffinwalaAuthService.Repository.VendorRepository;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class VendorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private RoleServices roleServices;

    @Autowired
    private PasswordEncoder passwordEncoder; // Add PasswordEncoder

    // Get all vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // Update vendor verification status
    public Vendor updateVerificationStatus(Integer vendorId, Boolean isVerified) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + vendorId));
        vendor.setIsVerified(isVerified);
        return vendorRepository.save(vendor);
    }

    // Save a new user and associated vendor if applicable
    @Transactional
    public void saveUser(RegDummy r) {
        try {
        	
        	System.out.println("In registration method");
            User user = new User();
            user.setFname(r.getFname());
            user.setLname(r.getLname());
            user.setEmail(r.getEmail());
            user.setPassword(passwordEncoder.encode(r.getPassword())); // Encode password
            user.setContact(r.getContact());

            Address address = new Address(r.getCity(), r.getState(), r.getPincode(), r.getArea());
            user.setAddress(address);

            Role role = roleServices.getRoleById(r.getRid());
            if (role == null) {
                throw new ResourceNotFoundException("Invalid role ID.");
            }
            user.setRole(role);

            userRepository.save(user);

            if (r.getRid() == 2) { // Vendor
                Vendor vendor = new Vendor();
                vendor.setIsVerified(false);
                vendor.setFoodLicenceNo(r.getFoodLicenceNo());
                vendor.setAdhar_no(r.getAdhar_no());
                vendor.setUser(user);
                vendorRepository.save(vendor);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Data integrity violation: Duplicate entry for email or contact.");
        }
    }

    // Get all approved vendors
    public List<Vendor> getAllApprovedVendors() {
        return vendorRepository.findByIsVerifiedTrue();
    }

    // Get all unapproved vendors
    public List<Vendor> getAllUnapprovedVendors() {
        return vendorRepository.findByIsVerifiedFalse();
    }

    // Get vendor by user ID
    public Vendor getVendorByUserId(Integer uid) {
        // Check if the user exists
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + uid));

        // Find vendor by user ID
        return vendorRepository.findVendorByUserUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found for User ID: " + uid));
    }
}
