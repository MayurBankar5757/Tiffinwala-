package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Dummy.VendorSubscriptionPlanDTO;
import Tiffinwala.App.Dummy.Vendor_Sub_Plan_Dummy;
import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import Tiffinwala.App.Repository.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendorSubscriptionPlanService {

    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;
    private final VendorRepository vendorRepository;
    @Autowired
    private VendorService vservice;

    public VendorSubscriptionPlanService(VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository, VendorRepository vendorRepository) {
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
        this.vendorRepository = vendorRepository;
    }
    public VendorSubscriptionPlan createSubscriptionPlan(VendorSubscriptionPlanDTO dto) {
        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + dto.getVendorId()));

        VendorSubscriptionPlan subscriptionPlan = new VendorSubscriptionPlan();
        subscriptionPlan.setVendor(vendor);
        subscriptionPlan.setName(dto.getName());
        subscriptionPlan.setPrice(dto.getPrice());
        subscriptionPlan.setDescription(dto.getDescription());
        subscriptionPlan.setIsAvailable(dto.isAvaliable());  // ✅ Works for "isAvailable" field

        subscriptionPlan.setDuration(dto.getDuration());  // ✅ Set ENUM value

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
    
    // upload photo
    
    @Transactional
    public boolean uploadPhoto(int id, byte[] file) {
        if (file == null || file.length == 0) {
            System.out.println("File is null or empty.");
            return false;
        }

        int updatedRows = vendorSubscriptionPlanRepository.uploadPhoto(id, file);
        System.out.println("Updated rows: " + updatedRows + " for Plan ID: " + id);
        return updatedRows > 0;
    }


    // Delete a subscription plan
    public void deleteSubscriptionPlanById(Integer planId) {
        vendorSubscriptionPlanRepository.deleteById(planId);
    }
    
    
    // Enabled subcription plan 
    public VendorSubscriptionPlan enableSubscriptionPlan(Integer planId) {
        VendorSubscriptionPlan plan = vendorSubscriptionPlanRepository.findByPlanId(planId);

        plan.setIsAvailable(true); // Enable the subscription plan
        return vendorSubscriptionPlanRepository.save(plan); // Save the updated plan
    }
    
    
    // disabled subcription plan
    
    public VendorSubscriptionPlan disableSubscriptionPlan(Integer planId) {
        VendorSubscriptionPlan plan = vendorSubscriptionPlanRepository.findByPlanId(planId);
              
        plan.setIsAvailable(false); // Disable the subscription plan
        return vendorSubscriptionPlanRepository.save(plan); // Save the updated plan
    }
    
    // display enabled subcription plan
    
    public List<VendorSubscriptionPlan> getEnabledSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findByIsAvailableTrue(); // Fetch all enabled plans
    }
    
    public List<VendorSubscriptionPlan> getDisabledSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findByIsAvailableFalse(); // Fetch all disabled plans
    }
}
