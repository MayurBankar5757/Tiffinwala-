package com.Tiffinwala.TiffinwalaCrudService.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerSubscribedPlans;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Entities.VendorSubscriptionPlan;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Repository.CustomerSubscribedPlansRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.UserRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorSubscriptionPlanRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerSubscribedPlansService {

    private final CustomerSubscribedPlansRepository customerSubscribedPlansRepository;
    private final UserRepository userRepository;
    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;

    @Autowired
    public CustomerSubscribedPlansService(CustomerSubscribedPlansRepository customerSubscribedPlansRepository,
                                          UserRepository userRepository,
                                          VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository) {
        this.customerSubscribedPlansRepository = customerSubscribedPlansRepository;
        this.userRepository = userRepository;
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
    }

    public CustomerSubscribedPlans createSubscriptionPlan(Integer userId, Integer subscriptionPlanId, LocalDate startDate, LocalDate orderedDate) {
        // Check if the user already has an active subscription for the given plan
        Optional<CustomerSubscribedPlans> existingPlan = customerSubscribedPlansRepository.getPlanByUidAndPlanId(userId, subscriptionPlanId);

        if (existingPlan.isPresent()) {
            return existingPlan.get(); // Return existing plan if found
        }

        // Fetch the user based on userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with UID " + userId + " not found"));

        // Fetch the subscription plan based on subscriptionPlanId
        VendorSubscriptionPlan vendorSubscriptionPlan = vendorSubscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription plan with ID " + subscriptionPlanId + " not found"));

        // Get the duration of the plan
        int durationInDays = vendorSubscriptionPlan.getDuration().getDays();

        // Calculate start and end date
        //LocalDate startDate = startDate;
        LocalDate endDate = startDate.plusDays(durationInDays);

        // Optionally log the duration for debugging (use a logger in production)
        System.out.println("Duration last date: " + durationInDays);

        // Create the new subscription plan
        CustomerSubscribedPlans customerSubscribedPlan = new CustomerSubscribedPlans(user, vendorSubscriptionPlan, startDate, endDate, orderedDate);

        // Save and return the new subscription plan
        return customerSubscribedPlansRepository.save(customerSubscribedPlan);
    }

    // Get Customer Subscription Plans by Vendor Subscription Plan ID
    @Transactional
    public List<CustomerSubscribedPlans> getSubscriptionPlansByVendorId(Integer planId) {
        List<CustomerSubscribedPlans> subscriptions = customerSubscribedPlansRepository.findByVendorSubscriptionPlanPlanId(planId);
        if (subscriptions.isEmpty()) {
            throw new ResourceNotFoundException("No subscriptions found for vendor plan ID " + planId);
        }
        return subscriptions;
    }

    // Get all customer subscription plans
    public List<CustomerSubscribedPlans> getAllSubscriptionPlans() {
        return customerSubscribedPlansRepository.findAll();
    }

    // Delete subscription plan by ID
    public void deleteSubscriptionPlanById(Integer id) {
        if (!customerSubscribedPlansRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subscription with ID " + id + " not found");
        }
        customerSubscribedPlansRepository.deleteById(id);
    }

    // Get subscription plan by userId (to show all plans for a user)
    public List<CustomerSubscribedPlans> getSubscriptionPlansByUserId(Integer uid) {
        List<CustomerSubscribedPlans> plans = customerSubscribedPlansRepository.findByUser_Uid(uid);
        if (plans.isEmpty()) {
            throw new ResourceNotFoundException("No subscriptions found for user with UID " + uid);
        }
        return plans;
    }
    
    
    // Scheduled task to delete expired plans daily at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at 00:00 (midnight)
    public void deleteExpiredPlans() {
        LocalDate today = LocalDate.now();
        List<CustomerSubscribedPlans> expiredPlans = customerSubscribedPlansRepository.findByEndDate(today);

        if (!expiredPlans.isEmpty()) {
            customerSubscribedPlansRepository.deleteAll(expiredPlans);
            System.out.println("Deleted expired customer plans: " + expiredPlans.size());
        }
    }

}
