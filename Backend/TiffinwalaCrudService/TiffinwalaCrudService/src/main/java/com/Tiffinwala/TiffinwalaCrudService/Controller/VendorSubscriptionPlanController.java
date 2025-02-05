package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import com.Tiffinwala.TiffinwalaCrudService.Dummy.VendorSubscriptionPlanDTO;
import com.Tiffinwala.TiffinwalaCrudService.Entities.VendorSubscriptionPlan;
import com.Tiffinwala.TiffinwalaCrudService.Services.VendorSubscriptionPlanService;

@RestController
@RequestMapping("/api/vendor-subscription-plans")
@CrossOrigin(origins = "http://localhost:3010")
public class VendorSubscriptionPlanController {

    @Autowired
    private VendorSubscriptionPlanService vendorSubscriptionPlanService;

    // Create Subscription Plan (Only VENDOR)
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(@RequestBody VendorSubscriptionPlanDTO dto) {
        VendorSubscriptionPlan newPlan = vendorSubscriptionPlanService.createSubscriptionPlan(dto);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    // Upload Image for a Subscription Plan (Only VENDOR)
    @PostMapping(value = "/uploadImage/{vid}", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<String> uploadImage(@PathVariable("vid") int vid, @RequestParam("file") MultipartFile file) {
        try {
            vendorSubscriptionPlanService.uploadPhoto(vid, file.getBytes());
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        }
    }

    // Get Subscription Plan by ID (Accessible by CUSTOMER & VENDOR)
    @GetMapping("/{planId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'VENDOR')")
    public ResponseEntity<VendorSubscriptionPlan> getSubscriptionPlanById(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlanById(planId));
    }

    // Get Subscription Plans for a Specific Vendor (Only VENDOR)
    @GetMapping("/vendor/{vendorId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<List<VendorSubscriptionPlan>> getSubscriptionPlansByVendorId(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlansByVendorId(vendorId));
    }

    // Get All Subscription Plans (Accessible by ADMIN or VENDOR)
    @GetMapping("/getAllSubcriptionPlan")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDOR')")
    public ResponseEntity<List<VendorSubscriptionPlan>> getAllSubscriptionPlans() {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getAllSubscriptionPlans());
    }

    // Delete a Subscription Plan (Only VENDOR)
    @DeleteMapping("/{planId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Integer planId) {
        vendorSubscriptionPlanService.deleteSubscriptionPlanById(planId);
        return ResponseEntity.noContent().build();
    }

    // Enable Subscription Plan (Only VENDOR)
    @PutMapping("/{planId}/enabled")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<VendorSubscriptionPlan> enableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.enableSubscriptionPlan(planId));
    }

    // Disable Subscription Plan (Only VENDOR)
    @PutMapping("/{planId}/disabled")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<VendorSubscriptionPlan> disableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.disableSubscriptionPlan(planId));
    }

    // Get Enabled Subscription Plans (Accessible by CUSTOMER & VENDOR)
    @GetMapping("/enabled")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'VENDOR')")
    public ResponseEntity<List<VendorSubscriptionPlan>> getEnabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> enabledPlans = vendorSubscriptionPlanService.getEnabledSubscriptionPlans();
        return new ResponseEntity<>(enabledPlans, HttpStatus.OK);
    }

    // Get Disabled Subscription Plans (Accessible by CUSTOMER & VENDOR)
    @GetMapping("/disabled")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'VENDOR')")
    public ResponseEntity<List<VendorSubscriptionPlan>> getDisabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> disabledPlans = vendorSubscriptionPlanService.getDisabledSubscriptionPlans();
        return new ResponseEntity<>(disabledPlans, HttpStatus.OK);
    }
    
    // filters
    
   

        @GetMapping("/filter/0-1000")
        public ResponseEntity<List<VendorSubscriptionPlan>> getPlansInRange0To1000() {
            List<VendorSubscriptionPlan> plans = vendorSubscriptionPlanService.getSubscriptionPlansByPriceRange(0, 1000);
            return new ResponseEntity<>(plans, HttpStatus.OK);
        }

        @GetMapping("/filter/1000-5000")
        public ResponseEntity<List<VendorSubscriptionPlan>> getPlansInRange1000To5000() {
            List<VendorSubscriptionPlan> plans = vendorSubscriptionPlanService.getSubscriptionPlansByPriceRange(1000, 5000);
            return new ResponseEntity<>(plans, HttpStatus.OK);
        }

        @GetMapping("/filter/5000+")
        public ResponseEntity<List<VendorSubscriptionPlan>> getPlansAbove5000() {
            List<VendorSubscriptionPlan> plans = vendorSubscriptionPlanService.getSubscriptionPlansAbovePrice(5000);
            return new ResponseEntity<>(plans, HttpStatus.OK);
        }
    

    
    
}
