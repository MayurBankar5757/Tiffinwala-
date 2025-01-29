package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
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

    // Save a new subscription plan
    public ResponseEntity<VendorSubscriptionPlan> saveSubscriptionPlan(Vendor_Sub_Plan_Dummy dummy) {
        System.out.println("Received isAvaliable: " + dummy.isAvaliable()); // Debug input
        Vendor vendor = vservice.getVendorById(dummy.getVid());
        VendorSubscriptionPlan plan = new VendorSubscriptionPlan();

        plan.setDescription(dummy.getDescription());
        plan.setIsAvailable(dummy.isAvaliable()); // Debug mapping
        System.out.println("Mapped isAvailable: " + plan.getIsAvailable()); // Debug entity field
        plan.setName(dummy.getName());
        plan.setPrice(dummy.getPrice());
        plan.setVendor(vendor);

        VendorSubscriptionPlan savedPlan = vendorSubscriptionPlanRepository.save(plan);
        System.out.println("Saved isAvailable: " + savedPlan.getIsAvailable()); // Debug saved value
        return ResponseEntity.ok(savedPlan);
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
