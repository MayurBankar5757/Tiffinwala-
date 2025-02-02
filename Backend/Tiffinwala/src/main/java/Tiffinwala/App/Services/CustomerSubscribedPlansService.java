package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Repository.CustomerSubscribedPlansRepository;
import Tiffinwala.App.Repository.UserRepository;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    // Create a subscription plan
    public CustomerSubscribedPlans createSubscriptionPlan(Integer userId, Integer subscriptionPlanId, LocalDate orderedDate) {
        // Fetch the user based on userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with UID " + userId + " not found"));

        // Fetch the subscription plan based on subscriptionPlanId
        VendorSubscriptionPlan vendorSubscriptionPlan = vendorSubscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription plan with ID " + subscriptionPlanId + " not found"));

        // Use the passed duration directly
        int durationInDays = vendorSubscriptionPlan.getDuration().getDays();

        // Calculate the startDate and endDate
        LocalDate startDate = orderedDate;
       
        LocalDate endDate = startDate.plusDays(durationInDays);
        System.out.println("Duration last date "+durationInDays );
        // Create the new subscription plan
        CustomerSubscribedPlans customerSubscribedPlan = new CustomerSubscribedPlans(user, vendorSubscriptionPlan, startDate, endDate, orderedDate);

        // Save the new subscription plan
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
}
