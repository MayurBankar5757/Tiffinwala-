package com.Tiffinwala.TiffinwalaCrudService.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.RegDummy;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Address;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Role;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ConflictException;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Repository.UserRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorRepository;

import jakarta.transaction.Transactional;

@Service
public class VendorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private RoleServices roleServices;

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
            User user = new User();
            user.setFname(r.getFname());
            user.setLname(r.getLname());
            user.setEmail(r.getEmail());
            user.setPassword(r.getPassword());
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
    
    public Vendor getVendorByUserId(Integer uid) {
        Optional<Vendor> vendor = vendorRepository.findById(uid);
        if (vendor.isPresent()) {
            return vendor.get();
        } else {
            throw new ResourceNotFoundException("Vendor not found with ID: " + uid);
        }
    }
}
