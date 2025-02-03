package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/create")
    public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(@RequestBody VendorSubscriptionPlanDTO dto) {
        VendorSubscriptionPlan newPlan = vendorSubscriptionPlanService.createSubscriptionPlan(dto);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @PostMapping(value = "/uploadImage/{vid}", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@PathVariable("vid") int vid, @RequestParam("file") MultipartFile file) {
        try {
			vendorSubscriptionPlanService.uploadPhoto(vid, file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @GetMapping("/{planId}")
    public ResponseEntity<VendorSubscriptionPlan> getSubscriptionPlanById(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlanById(planId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorSubscriptionPlan>> getSubscriptionPlansByVendorId(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlansByVendorId(vendorId));
    }

    @GetMapping("/getAllSubcriptionPlan")
    public ResponseEntity<List<VendorSubscriptionPlan>> getAllSubscriptionPlans() {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getAllSubscriptionPlans());
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Integer planId) {
        vendorSubscriptionPlanService.deleteSubscriptionPlanById(planId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{planId}/enabled")
    public ResponseEntity<VendorSubscriptionPlan> enableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.enableSubscriptionPlan(planId));
    }

    @PutMapping("/{planId}/disabled")
    public ResponseEntity<VendorSubscriptionPlan> disableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.disableSubscriptionPlan(planId));
    }
    @GetMapping("/enabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getEnabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> enabledPlans = vendorSubscriptionPlanService.getEnabledSubscriptionPlans();
        return new ResponseEntity<>(enabledPlans, HttpStatus.OK);
    }
    
    
    // display all disabled plans
    @GetMapping("/disabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getDisabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> disabledPlans = vendorSubscriptionPlanService.getDisabledSubscriptionPlans();
        return new ResponseEntity<>(disabledPlans, HttpStatus.OK);
    } 
}
