package com.Tiffinwala.TiffinwalaAuthService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaAuthService.Dummy.RegDummy;
import com.Tiffinwala.TiffinwalaAuthService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaAuthService.Services.VendorService;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:3010")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Get all vendors
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    // Change vendor verification status for admin
    @PutMapping("/{id}/verify")
    public ResponseEntity<Vendor> updateVerificationStatus(
            @PathVariable Integer id, @RequestParam Boolean isVerified) {
        Vendor updatedVendor = vendorService.updateVerificationStatus(id, isVerified);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }

    // Register a new user
    @PostMapping("/RegUser")
    public ResponseEntity<String> saveUser(@RequestBody RegDummy r) {
        try {
            vendorService.saveUser(r);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving user: " + e.getMessage());
        }
    }

    // Get all approved vendors for admin
    @GetMapping("/getAllApprovedVendor")
    public ResponseEntity<List<Vendor>> getAllApprovedVendors() {
        List<Vendor> vendors = vendorService.getAllApprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    // Get all unapproved vendors for admin
    @GetMapping("/getBlockedVendors")
    public ResponseEntity<List<Vendor>> getAllUnapprovedVendors() {
        List<Vendor> vendors = vendorService.getAllUnapprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    // Get vendor by user ID
    @GetMapping("/vendor/{uid}")
    public ResponseEntity<Vendor> getVendorByUserUid(@PathVariable Integer uid) {
        Vendor vendor = vendorService.getVendorByUserId(uid);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
