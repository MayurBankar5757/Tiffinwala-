package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.CustomerSubscriptionDTO;
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
@CrossOrigin(origins = "http://localhost:3010")
public class CustomerSubscribedPlansController {

    private final CustomerSubscribedPlansService customerSubscribedPlansService;

    @Autowired
    public CustomerSubscribedPlansController(CustomerSubscribedPlansService customerSubscribedPlansService) {
        this.customerSubscribedPlansService = customerSubscribedPlansService;
    }

    // Endpoint to create a subscription
    @PostMapping("/subscribePlan")
    public ResponseEntity<?> createSubscription(@RequestBody CustomerSubscriptionDTO subscriptionDTO) {
        try {
            LocalDate liveDate = LocalDate.now();
            CustomerSubscribedPlans createdPlan = customerSubscribedPlansService.createSubscriptionPlan(
                    subscriptionDTO.getUserId(),
                    subscriptionDTO.getSubscriptionPlanId(),
                    liveDate
            );
            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/planid/{planId}")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionBySubcriptionId(@PathVariable Integer planId) {
        List<CustomerSubscribedPlans> subscriptions = customerSubscribedPlansService.getSubscriptionPlansByVendorId(planId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerSubscribedPlans>> getAllSubscriptions() {
        List<CustomerSubscribedPlans> allSubscriptions = customerSubscribedPlansService.getAllSubscriptionPlans();
        return new ResponseEntity<>(allSubscriptions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Integer id) {
        try {
            customerSubscribedPlansService.deleteSubscriptionPlanById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: Subscription not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<List<CustomerSubscribedPlans>> getSubscriptionPlansByUserId(@PathVariable Integer uid) {
        List<CustomerSubscribedPlans> plans = customerSubscribedPlansService.getSubscriptionPlansByUserId(uid);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
