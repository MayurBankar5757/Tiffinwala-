package Tiffinwala.App.Controller;

import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Services.CustomerSubscribedPlansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class CustomerSubscribedPlansController {

    private final CustomerSubscribedPlansService customerSubscribedPlansService;

    public CustomerSubscribedPlansController(CustomerSubscribedPlansService customerSubscribedPlansService) {
        this.customerSubscribedPlansService = customerSubscribedPlansService;
    }

    // Create a new subscription
    @PostMapping
    public ResponseEntity<CustomerSubscribedPlans> createSubscription(
            @RequestParam Integer uid,
            @RequestParam Integer planId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        CustomerSubscribedPlans savedSubscription = customerSubscribedPlansService.saveSubscriptionPlan(
                uid, planId, LocalDate.parse(startDate), LocalDate.parse(endDate)
        );
        return new ResponseEntity<>(savedSubscription, HttpStatus.CREATED);
    }

    // Get subscription by user ID
    @GetMapping("/user/{uid}")
    public ResponseEntity<CustomerSubscribedPlans> getSubscriptionByUserId(@PathVariable Integer uid) {
        return customerSubscribedPlansService.getSubscriptionPlanByUserId(uid)
                .map(subscription -> new ResponseEntity<>(subscription, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
}
