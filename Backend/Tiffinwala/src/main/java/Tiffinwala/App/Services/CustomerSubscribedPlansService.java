package Tiffinwala.App.Services;
import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Repository.CustomerSubscribedPlansRepository;
import Tiffinwala.App.Repository.UserRepository;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerSubscribedPlansService {

    @Autowired
    private CustomerSubscribedPlansRepository customerSubscribedPlansRepository;

    @Autowired
    private VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a subscription plan for a user
    public CustomerSubscribedPlans createSubscriptionPlan(Integer userId, Integer subscriptionPlanId, LocalDate orderedDate) {
        // Fetch the user and subscription plan based on the provided IDs
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with UID " + userId + " not found"));

        VendorSubscriptionPlan vendorSubscriptionPlan = vendorSubscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new RuntimeException("Subscription plan with ID " + subscriptionPlanId + " not found"));

        // Get the duration (in days) from the VendorSubscriptionPlan (it's an enum)
        int durationInDays = vendorSubscriptionPlan.getDuration().getDays(); // Use getDays() to get the integer value

        // Calculate the startDate and endDate
        LocalDate startDate = orderedDate;
        LocalDate endDate = startDate.plusDays(durationInDays);

        // Create the new CustomerSubscribedPlans object
        CustomerSubscribedPlans customerSubscribedPlan = new CustomerSubscribedPlans(user, vendorSubscriptionPlan, startDate, endDate, orderedDate);

        // Save and return the newly created subscription plan
        return customerSubscribedPlansRepository.save(customerSubscribedPlan);
    }
    // Get Customer Subscription Plans by User UID
    public Optional<CustomerSubscribedPlans> getSubscriptionPlanByUserId(Integer uid) {
        return customerSubscribedPlansRepository.findByUserUid(uid);
    }

    // Get Customer Subscription Plans by Vendor Subscription Plan ID
    public List<CustomerSubscribedPlans> getSubscriptionPlansByVendorId(Integer planId) {
        return customerSubscribedPlansRepository.findByVendorSubscriptionPlanPlanId(planId);
    }

    // Get all customer subscription plans
    public List<CustomerSubscribedPlans> getAllSubscriptionPlans() {
        return customerSubscribedPlansRepository.findAll();
    }

    // Delete subscription plan by ID
    public void deleteSubscriptionPlanById(Integer id) {
        customerSubscribedPlansRepository.deleteById(id);
    }

    // Get subscription plan by userId (to show all plans for a user)
    public List<CustomerSubscribedPlans> getSubscriptionPlansByUserId(Integer uid) {
        return customerSubscribedPlansRepository.findByUser_Uid(uid);
    }
}
