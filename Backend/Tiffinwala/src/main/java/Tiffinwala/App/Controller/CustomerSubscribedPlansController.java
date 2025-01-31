package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.CustomerSubscriptionDTO;
import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Enum.SubscriptionDuration;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
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

	 private final CustomerSubscribedPlansService customerSubscribedPlansService;

	    @Autowired
	    public CustomerSubscribedPlansController(CustomerSubscribedPlansService customerSubscribedPlansService) {
	        this.customerSubscribedPlansService = customerSubscribedPlansService;
	    }

	    // Endpoint to create a subscription
	    @PostMapping("/create")
	    public ResponseEntity<?> createSubscription(@RequestBody CustomerSubscriptionDTO subscriptionDTO) {
	        try {
	            // Get the current date (live date)
	            LocalDate liveDate = LocalDate.now();

	            // Call service method to create a subscription with the live date
	            CustomerSubscribedPlans createdPlan = customerSubscribedPlansService.createSubscriptionPlan(
	                    subscriptionDTO.getUserId(),
	                    subscriptionDTO.getSubscriptionPlanId(),
	                    liveDate,  // Set live date as orderedDate
	                    subscriptionDTO.getDuration()  // Pass the duration from React
	            );

	            // Return the created plan in the response
	            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
	        } catch (RuntimeException e) {
	            // Handle any errors that occur during plan creation
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
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
    public ResponseEntity<?> deleteSubscription(@PathVariable Integer id) {
        try {
            customerSubscribedPlansService.deleteSubscriptionPlanById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: Subscription not found.", HttpStatus.NOT_FOUND);
        }
    }

    // Get all subscription plans for a specific user
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionPlansByUserId(@PathVariable Integer uid) {
        List<CustomerSubscribedPlans> plans = customerSubscribedPlansService.getSubscriptionPlansByUserId(uid);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
