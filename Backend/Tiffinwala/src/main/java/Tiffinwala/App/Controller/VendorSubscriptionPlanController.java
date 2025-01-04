package Tiffinwala.App.Controller;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Services.VendorSubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-subscription-plans")
@CrossOrigin(origins = "http://localhost:3000")
public class VendorSubscriptionPlanController {

    private final VendorSubscriptionPlanService vendorSubscriptionPlanService;

    public VendorSubscriptionPlanController(VendorSubscriptionPlanService vendorSubscriptionPlanService) {
        this.vendorSubscriptionPlanService = vendorSubscriptionPlanService;
    }

    // Create a new subscription plan
    @PostMapping
    public ResponseEntity<VendorSubscriptionPlan> createSubscriptionPlan(
            @RequestParam Integer vendorId,
            @RequestParam String name,
            @RequestParam Integer price,
            @RequestParam String description,
            @RequestParam String image,
            @RequestParam Boolean isAvailable) {

        VendorSubscriptionPlan savedSubscriptionPlan = vendorSubscriptionPlanService.saveSubscriptionPlan(
                vendorId, name, price, description, image, isAvailable
        );
        return new ResponseEntity<>(savedSubscriptionPlan, HttpStatus.CREATED);
    }

    // Get a subscription plan by ID
    @GetMapping("/{planId}")
    public ResponseEntity<VendorSubscriptionPlan> getSubscriptionPlanById(@PathVariable Integer planId) {
        VendorSubscriptionPlan subscriptionPlan = vendorSubscriptionPlanService.getSubscriptionPlanById(planId);
        if (subscriptionPlan == null) {
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
