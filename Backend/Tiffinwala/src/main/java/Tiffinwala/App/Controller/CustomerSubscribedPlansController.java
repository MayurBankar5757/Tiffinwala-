package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.CustomerPlanDummy;
import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Services.CustomerSubscribedPlansService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class CustomerSubscribedPlansController {

	@Autowired
    private final CustomerSubscribedPlansService customerSubscribedPlansService;

    public CustomerSubscribedPlansController(CustomerSubscribedPlansService customerSubscribedPlansService) {
        this.customerSubscribedPlansService = customerSubscribedPlansService;
    }

    // Create a new subscription
    public ResponseEntity<CustomerSubscribedPlans> createSubscription(@RequestParam Integer userId,
            @RequestParam Integer subscriptionPlanId,
            @RequestParam LocalDate orderedDate) {
    		// Call service method to create a subscription plan
    		CustomerSubscribedPlans createdPlan = customerSubscribedPlansService.createSubscriptionPlan(userId, subscriptionPlanId, orderedDate);

    		// Return the created plan in the response
    		return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    	}


    // Get all subscriptions by vendor plan ID
    @GetMapping("/vendor/{planId}")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionByVendorId(@PathVariable Integer planId) {
        List<CustomerSubscribedPlans> subscriptions = customerSubscribedPlansService.getSubscriptionPlansByVendorId(planId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    // Get all subscriptions
    @GetMapping
    public ResponseEntity<List<CustomerSubscribedPlans>> getAllSubscriptions() {
        List<CustomerSubscribedPlans> allSubscriptions = customerSubscribedPlansService.getAllSubscriptionPlans();
        return new ResponseEntity<>(allSubscriptions, HttpStatus.OK);
    }

    // Delete subscription by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {
        customerSubscribedPlansService.deleteSubscriptionPlanById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // get all subcription plan for specific vendor 
    
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionPlansByUserId(@PathVariable Integer uid) {
        List<CustomerSubscribedPlans> plans = customerSubscribedPlansService.getSubscriptionPlansByUserId(uid);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
