package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Repository.CustomerSubscribedPlansRepository;
import Tiffinwala.App.Repository.UserRepository;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerSubscribedPlansService {

    private final CustomerSubscribedPlansRepository customerSubscribedPlansRepository;
    private final UserRepository userRepository;
    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;

    public CustomerSubscribedPlansService(
            CustomerSubscribedPlansRepository customerSubscribedPlansRepository,
            UserRepository userRepository,
            VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository) {
        this.customerSubscribedPlansRepository = customerSubscribedPlansRepository;
        this.userRepository = userRepository;
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
    }

    // Save a new customer subscription plan
    public CustomerSubscribedPlans saveSubscriptionPlan(Integer uid, Integer planId, LocalDate startDate, LocalDate endDate) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with UID " + uid + " not found");
        }

        Optional<VendorSubscriptionPlan> vendorSubscriptionPlan = vendorSubscriptionPlanRepository.findById(planId);
        if (vendorSubscriptionPlan.isEmpty()) {
            throw new IllegalArgumentException("Vendor Subscription Plan with ID " + planId + " not found");
        }

        CustomerSubscribedPlans customerSubscribedPlans = new CustomerSubscribedPlans(
                user.get(), vendorSubscriptionPlan.get(), startDate, endDate
        );
        return customerSubscribedPlansRepository.save(customerSubscribedPlans);
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
}
