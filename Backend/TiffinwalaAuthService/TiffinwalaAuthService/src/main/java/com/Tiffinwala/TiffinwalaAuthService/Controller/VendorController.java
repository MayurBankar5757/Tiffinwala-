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
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3010")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Register a new user
    @PostMapping("/RegUser")
    public ResponseEntity<String> saveUser(@RequestBody RegDummy r) {
    	System.out.println("");
    	
        try {
            vendorService.saveUser(r);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving user: " + e.getMessage());
        }
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
