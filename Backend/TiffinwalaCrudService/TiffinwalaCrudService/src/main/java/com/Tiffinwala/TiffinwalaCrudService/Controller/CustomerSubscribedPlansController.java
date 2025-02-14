package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.CustomerSubscriptionDTO;
import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerSubscribedPlans;
import com.Tiffinwala.TiffinwalaCrudService.Services.CustomerSubscribedPlansService;

@RestController
@RequestMapping("/api2")
//@CrossOrigin(origins = "http://localhost:3010")
public class CustomerSubscribedPlansController {

    private final CustomerSubscribedPlansService customerSubscribedPlansService;

    @Autowired
    public CustomerSubscribedPlansController(CustomerSubscribedPlansService customerSubscribedPlansService) {
        this.customerSubscribedPlansService = customerSubscribedPlansService;
    }

    // Endpoint to create a subscription
    @PostMapping("/csp/subscribePlan")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> createSubscription(@RequestBody CustomerSubscriptionDTO subscriptionDTO) {
        try {
            LocalDate orderedDate = LocalDate.now(); // Renamed from liveDate for clarity
            CustomerSubscribedPlans createdPlan = customerSubscribedPlansService.createSubscriptionPlan(
                    subscriptionDTO.getUserId(),
                    subscriptionDTO.getSubscriptionPlanId(),
                    subscriptionDTO.getStartDate(), // Get from DTO
                    orderedDate
            );
            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/csp/planid/{planId}")
    @PreAuthorize("hasAuthority('CUSTOMER','VENDOR')") // access in by vendor id
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionBySubcriptionId(@PathVariable Integer planId) {
        List<CustomerSubscribedPlans> subscriptions = customerSubscribedPlansService.getSubscriptionPlansByVendorId(planId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/csp/getAllSubscriptions")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','VENDOR')")
    public ResponseEntity<List<CustomerSubscribedPlans>> getAllSubscriptions() {
        List<CustomerSubscribedPlans> allSubscriptions = customerSubscribedPlansService.getAllSubscriptionPlans();
        return new ResponseEntity<>(allSubscriptions, HttpStatus.OK);
    }

    @DeleteMapping("/csp/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> deleteSubscription(@PathVariable Integer id) {
        try {
            customerSubscribedPlansService.deleteSubscriptionPlanById(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Subscription deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Subscription not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/csp/user/{uid}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','VENDOR')")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionPlansByUserId(@PathVariable Integer uid) {
        List<CustomerSubscribedPlans> plans = customerSubscribedPlansService.getSubscriptionPlansByUserId(uid);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
