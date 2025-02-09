package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.awt.PageAttributes.MediaType;
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
import com.Tiffinwala.TiffinwalaCrudService.Enum.SubscriptionDuration;
import com.Tiffinwala.TiffinwalaCrudService.Services.VendorSubscriptionPlanService;

@RestController
@RequestMapping("/api/vendor-subscription-plans")
@CrossOrigin(origins = "http://localhost:3010")
public class VendorSubscriptionPlanController {

    @Autowired
    private VendorSubscriptionPlanService vendorSubscriptionPlanService;


    public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(@RequestBody VendorSubscriptionPlanDTO dto) {
        VendorSubscriptionPlan newPlan = vendorSubscriptionPlanService.createSubscriptionPlan(dto);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }
    
    @PostMapping(
    	    value = "/create",
    	    consumes = "multipart/form-data" // Use string directly if still having issues
    	)
    	public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(
    	        @RequestParam("vendorId") Integer vendorId,
    	        @RequestParam("name") String name,
    	        @RequestParam("price") Integer price,
    	        @RequestParam("description") String description,
    	        @RequestParam("duration") SubscriptionDuration duration,
    	        @RequestParam(value = "image", required = false) MultipartFile image) {
    	    
    	    // Your existing implementation
    	    VendorSubscriptionPlanDTO dto = new VendorSubscriptionPlanDTO();
    	    dto.setVendorId(vendorId);
    	    dto.setName(name);
    	    dto.setPrice(price);
    	    dto.setDescription(description);
    	    dto.setDuration(duration);
    	    dto.setImage(image);

    	    VendorSubscriptionPlan newPlan = vendorSubscriptionPlanService.createSubscriptionPlan(dto);
    	    return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    	}
    
    // update subcription plan
    
    @PutMapping(
    	    value = "/update/{planId}",
    	    consumes = "multipart/form-data"
    	)
    	public ResponseEntity<VendorSubscriptionPlan> updateSubscriptionPlan(
    	        @PathVariable("planId") Integer planId,
    	        @RequestParam("name") String name,
    	        @RequestParam("price") Integer price,
    	        @RequestParam("description") String description,
    	        @RequestParam("duration") SubscriptionDuration duration,
    	        @RequestParam(value = "image", required = false) MultipartFile image) {

    	    VendorSubscriptionPlanDTO dto = new VendorSubscriptionPlanDTO();
    	    dto.setName(name);
    	    dto.setPrice(price);
    	    dto.setDescription(description);
    	    dto.setDuration(duration);
    	    dto.setImage(image); // Nullable, so image update is optional

    	    VendorSubscriptionPlan updatedPlan = vendorSubscriptionPlanService.updateSubscriptionPlan(dto, planId);
    	    return ResponseEntity.ok(updatedPlan);
    	}


    // Upload Image for a Subscription Plan (Now Open to All)
    @PostMapping(value = "/uploadImage/{vid}", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@PathVariable("vid") int vid, @RequestParam("file") MultipartFile file) {
        try {
            vendorSubscriptionPlanService.uploadPhoto(vid, file.getBytes());
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        }
    }

    // Get Subscription Plan by ID (Now Open to All)
    @GetMapping("/{planId}")
    public ResponseEntity<VendorSubscriptionPlan> getSubscriptionPlanById(@PathVariable Integer planId) {
        System.out.println("getSubscriptionPlanById hit");
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlanById(planId));
    }

    // Get Subscription Plans for a Specific Vendor (Now Open to All)
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorSubscriptionPlan>> getSubscriptionPlansByVendorId(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.getSubscriptionPlansByVendorId(vendorId));
    }

    // Get All Subscription Plans (Now Open to All)
    @GetMapping("/getAllSubcriptionPlan")
    public ResponseEntity<List<VendorSubscriptionPlan>> getAllSubscriptionPlans() {
        System.out.println("getAllSubscriptionPlans hit");
        return ResponseEntity.ok(vendorSubscriptionPlanService.getAllSubscriptionPlans());
    }

    // Delete a Subscription Plan (Now Open to All)
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Integer planId) {
        vendorSubscriptionPlanService.deleteSubscriptionPlanById(planId);
        return ResponseEntity.noContent().build();
    }

    // Enable Subscription Plan (Now Open to All)
    @PutMapping("/{planId}/enabled")
    public ResponseEntity<VendorSubscriptionPlan> enableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.enableSubscriptionPlan(planId));
    }

    // Disable Subscription Plan (Now Open to All)
    @PutMapping("/{planId}/disabled")
    public ResponseEntity<VendorSubscriptionPlan> disableSubscriptionPlan(@PathVariable Integer planId) {
        return ResponseEntity.ok(vendorSubscriptionPlanService.disableSubscriptionPlan(planId));
    }

    // Get Enabled Subscription Plans (Now Open to All)
    @GetMapping("/enabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getEnabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> enabledPlans = vendorSubscriptionPlanService.getEnabledSubscriptionPlans();
        return new ResponseEntity<>(enabledPlans, HttpStatus.OK);
    }

    // Get Disabled Subscription Plans (Now Open to All)
    @GetMapping("/disabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getDisabledSubscriptionPlans() {
        List<VendorSubscriptionPlan> disabledPlans = vendorSubscriptionPlanService.getDisabledSubscriptionPlans();
        return new ResponseEntity<>(disabledPlans, HttpStatus.OK);
    }

    // Filters
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
    
    @GetMapping("/vendor/{vendorId}/enabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getEnabledPlansByVendorId(@PathVariable Integer vendorId) {
        List<VendorSubscriptionPlan> enabledPlans = vendorSubscriptionPlanService.getEnabledPlansByVendorId(vendorId);
        if(enabledPlans.isEmpty()) {
        	return new ResponseEntity<>(enabledPlans, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(enabledPlans, HttpStatus.OK);
    }
    @GetMapping("/vendor/{vendorId}/disabled")
    public ResponseEntity<List<VendorSubscriptionPlan>> getDisabledPlansByVendorId(@PathVariable Integer vendorId) {
        List<VendorSubscriptionPlan> disabledPlans = vendorSubscriptionPlanService.getDisabledPlansByVendorId(vendorId);
        if (disabledPlans.isEmpty()) {
        	System.out.println("In Empty block");
            return new ResponseEntity<>(disabledPlans,HttpStatus.NO_CONTENT);
        }
    	System.out.println("In Outside block");

        return new ResponseEntity<>(disabledPlans, HttpStatus.OK);
    }
}
