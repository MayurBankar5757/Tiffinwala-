package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.VendorSubscriptionPlanDTO;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Enum.SubscriptionDuration;
import Tiffinwala.App.Services.VendorSubscriptionPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/vendor-subscription-plans")
public class VendorSubscriptionPlanController {

    @Autowired
    private VendorSubscriptionPlanService vendorSubscriptionPlanService;

    @PostMapping("/create")
    public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(
            @RequestParam("vendorId") Integer vendorId,
            @RequestParam("name") String name,
            @RequestParam("price") Integer price,
            @RequestParam("description") String description,
            @RequestParam("isAvaliable") boolean isAvaliable,
            @RequestParam("duration") SubscriptionDuration duration,
            @RequestParam(value = "image", required = false) MultipartFile image) {  // Accept image file

        VendorSubscriptionPlanDTO dto = new VendorSubscriptionPlanDTO();
        dto.setVendorId(vendorId);
        dto.setName(name);
        dto.setPrice(price);
        dto.setDescription(description);
        dto.setAvaliable(isAvaliable);
        dto.setDuration(duration);
        dto.setImage(image);  // Set the image file

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
