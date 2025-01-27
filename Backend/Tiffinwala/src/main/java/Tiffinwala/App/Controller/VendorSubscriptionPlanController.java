package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.Vendor_Sub_Plan_Dummy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;

import Tiffinwala.App.Services.VendorSubscriptionPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-subscription-plans")
@CrossOrigin(origins = "http://localhost:3000")
public class VendorSubscriptionPlanController {

	@Autowired
    private  VendorSubscriptionPlanService vendorSubscriptionPlanService;

 

    // Create a new subscription plan
	@PostMapping
	public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(@RequestBody Vendor_Sub_Plan_Dummy dummy) {
	    System.out.println("Received JSON: " + dummy);
	    return vendorSubscriptionPlanService.saveSubscriptionPlan(dummy);
	}

    @PostMapping(value = "/uploadImage/{vid}", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@PathVariable("vid") int vid, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            boolean isUploaded = vendorSubscriptionPlanService.uploadPhoto(vid, file.getBytes());

            if (isUploaded) {
                return ResponseEntity.ok("Image uploaded successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during image upload.");
        }
    }



    // Get a subscription plan by ID
    @GetMapping("/{planId}")
    public ResponseEntity<VendorSubscriptionPlan> getSubscriptionPlanById(@PathVariable Integer planId) {
        VendorSubscriptionPlan subscriptionPlan = vendorSubscriptionPlanService.getSubscriptionPlanById(planId);
      
        if(subscriptionPlan == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscriptionPlan, HttpStatus.OK);
    }

    // Get all subscription plans by Vendor ID
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorSubscriptionPlan>> getSubscriptionPlansByVendorId(@PathVariable Integer vendorId) {
        List<VendorSubscriptionPlan> subscriptionPlans = vendorSubscriptionPlanService.getSubscriptionPlansByVendorId(vendorId);
        return new ResponseEntity<>(subscriptionPlans, HttpStatus.OK);
    }

    // Get all subscription plans
    @GetMapping("/getAllSubcriptionPlan")
    public ResponseEntity<List<VendorSubscriptionPlan>> getAllSubscriptionPlans() {
        List<VendorSubscriptionPlan> allPlans = vendorSubscriptionPlanService.getAllSubscriptionPlans();
        return new ResponseEntity<>(allPlans, HttpStatus.OK);
    }

    // Delete a subscription plan
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Integer planId) {
        vendorSubscriptionPlanService.deleteSubscriptionPlanById(planId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
