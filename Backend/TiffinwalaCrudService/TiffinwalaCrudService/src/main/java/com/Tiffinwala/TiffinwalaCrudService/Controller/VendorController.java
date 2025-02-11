package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.RegDummy;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaCrudService.Services.VendorService;

@RestController
@RequestMapping("/api2")
//@CrossOrigin(origins = "http://localhost:3010")
public class VendorController {

    @Autowired
    private VendorService vendorService;

//    // Get all Vendors
//    @GetMapping
//    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')") 
//    public ResponseEntity<List<Vendor>> getAllVendors() {
//        List<Vendor> vendors = vendorService.getAllVendors();
//        return new ResponseEntity<>(vendors, HttpStatus.OK);
//    }

    // Change Vendor Verification Status for admin
    @PutMapping("/vendor/{id}/verify")
    @PreAuthorize("hasAuthority('ADMIN')") 
    public ResponseEntity<Vendor> updateVerificationStatus(
            @PathVariable Integer id, @RequestParam Boolean isVerified) {
        Vendor updatedVendor = vendorService.updateVerificationStatus(id, isVerified);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }

   

    // Get all approved vendors for admin
    @GetMapping("/vendor/getAllApprovedVendor")
    @PreAuthorize("hasAuthority('ADMIN')") 
    public ResponseEntity<List<Vendor>> getAllApprovedVendors() {
        List<Vendor> vendors = vendorService.getAllApprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    // Get all unapproved vendors for admin
    @GetMapping("/vendor/getBlockedVendors")
    @PreAuthorize("hasAuthority('ADMIN')") 
    public ResponseEntity<List<Vendor>> getAllUnapprovedVendors() {
        List<Vendor> vendors = vendorService.getAllUnapprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    @GetMapping("/vendor/vendor/{uid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','VENDOR')") 
    public ResponseEntity<Vendor> getVendorByUserUid(@PathVariable Integer uid) {
        Vendor vendor = vendorService.getVendorByUserId(uid);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
