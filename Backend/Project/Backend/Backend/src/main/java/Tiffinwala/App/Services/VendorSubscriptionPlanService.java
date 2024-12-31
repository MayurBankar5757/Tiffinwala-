package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import Tiffinwala.App.Repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorSubscriptionPlanService {

    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;
    private final VendorRepository vendorRepository;

    public VendorSubscriptionPlanService(VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository, VendorRepository vendorRepository) {
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
        this.vendorRepository = vendorRepository;
    }

    // Save a new subscription plan
    public VendorSubscriptionPlan saveSubscriptionPlan(Integer vendorId, String name, Integer price, String description, String image, Boolean isAvailable) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        VendorSubscriptionPlan subscriptionPlan = new VendorSubscriptionPlan(vendor, name, price, description, image, isAvailable);
        return vendorSubscriptionPlanRepository.save(subscriptionPlan);
    }

    // Get subscription plan by ID
    public VendorSubscriptionPlan getSubscriptionPlanById(Integer planId) {
        return vendorSubscriptionPlanRepository.findByPlanId(planId);
    }

    // Get subscription plans by Vendor ID
    public List<VendorSubscriptionPlan> getSubscriptionPlansByVendorId(Integer vendorId) {
        return vendorSubscriptionPlanRepository.findByVendorVendorId(vendorId);
    }

    // Get all subscription plans
    public List<VendorSubscriptionPlan> getAllSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findAll();
    }

    // Delete a subscription plan
    public void deleteSubscriptionPlanById(Integer planId) {
        vendorSubscriptionPlanRepository.deleteById(planId);
    }
}
