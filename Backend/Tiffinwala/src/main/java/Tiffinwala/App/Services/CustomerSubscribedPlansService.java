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

	    public CustomerSubscribedPlans createSubscriptionPlan(Integer userId, Integer subscriptionPlanId, LocalDate orderedDate, Integer duration) {
	        // Fetch the user based on userId
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User with UID " + userId + " not found"));

	        // Fetch the subscription plan based on subscriptionPlanId
	        VendorSubscriptionPlan vendorSubscriptionPlan = vendorSubscriptionPlanRepository.findById(subscriptionPlanId)
	                .orElseThrow(() -> new RuntimeException("Subscription plan with ID " + subscriptionPlanId + " not found"));

	        // Use the passed duration directly
	        int durationInDays = duration;

	        // Calculate the startDate and endDate
	        LocalDate startDate = orderedDate;
	        LocalDate endDate = startDate.plusDays(durationInDays);

	        // Create the new subscription plan
	        CustomerSubscribedPlans customerSubscribedPlan = new CustomerSubscribedPlans(user, vendorSubscriptionPlan, startDate, endDate, orderedDate);
	        
	        // Save the new subscription plan
	        return customerSubscribedPlansRepository.save(customerSubscribedPlan);
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
