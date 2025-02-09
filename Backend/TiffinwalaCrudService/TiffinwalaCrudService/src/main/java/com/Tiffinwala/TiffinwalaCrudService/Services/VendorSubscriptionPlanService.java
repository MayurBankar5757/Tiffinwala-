package com.Tiffinwala.TiffinwalaCrudService.Services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.VendorSubscriptionPlanDTO;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaCrudService.Entities.VendorSubscriptionPlan;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ConflictException;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorSubscriptionPlanRepository;

import jakarta.transaction.Transactional;

@Service
public class VendorSubscriptionPlanService {

    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorSubscriptionPlanService(VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository, VendorRepository vendorRepository) {
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
        this.vendorRepository = vendorRepository;
    }

    public VendorSubscriptionPlan createSubscriptionPlan(VendorSubscriptionPlanDTO dto) {
        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + dto.getVendorId()));

        VendorSubscriptionPlan subscriptionPlan = new VendorSubscriptionPlan();
        subscriptionPlan.setVendor(vendor);
        subscriptionPlan.setName(dto.getName());
        subscriptionPlan.setPrice(dto.getPrice());
        subscriptionPlan.setDescription(dto.getDescription());
        subscriptionPlan.setIsAvailable(dto.isAvaliable());
        subscriptionPlan.setDuration(dto.getDuration());
        try {
			subscriptionPlan.setImage(dto.getImage().getBytes());
			System.out.println("Image "+dto.getImage().getBytes() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

        System.out.println("subcription plan dto : "+dto);
        try {
            return vendorSubscriptionPlanRepository.save(subscriptionPlan);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Duplicate entry detected. Please ensure all fields are unique.");
        }
    }
    
    // update subcription plan
    
    public VendorSubscriptionPlan updateSubscriptionPlan(VendorSubscriptionPlanDTO dto,int planId) {
        VendorSubscriptionPlan existingPlan = vendorSubscriptionPlanRepository.findById(planId)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription Plan not found with ID: " + planId));

        existingPlan.setName(dto.getName());
        existingPlan.setPrice(dto.getPrice());
        existingPlan.setDescription(dto.getDescription());
        existingPlan.setDuration(dto.getDuration());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                existingPlan.setImage(dto.getImage().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing image", e);
            }
        }

        return vendorSubscriptionPlanRepository.save(existingPlan);
    }

    
    //get sub plan by id

    public VendorSubscriptionPlan getSubscriptionPlanById(Integer planId) {
        return Optional.ofNullable(vendorSubscriptionPlanRepository.findByPlanId(planId))
                .orElseThrow(() -> new ResourceNotFoundException("Subscription Plan not found with ID: " + planId));
    }

    public List<VendorSubscriptionPlan> getSubscriptionPlansByVendorId(Integer vendorId) {
        return vendorSubscriptionPlanRepository.findByVendorVendorId(vendorId);
    }

    public List<VendorSubscriptionPlan> getAllSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findAll();
    }

    @Transactional
    public boolean uploadPhoto(int id, byte[] file) {
        if (file == null || file.length == 0) {
            throw new ConflictException("File is null or empty.");
        }
        int updatedRows = vendorSubscriptionPlanRepository.uploadPhoto(id, file);
        return updatedRows > 0;
    }

    public void deleteSubscriptionPlanById(Integer planId) {
        if (!vendorSubscriptionPlanRepository.existsById(planId)) {
            throw new ResourceNotFoundException("Subscription Plan not found with ID: " + planId);
        }
        vendorSubscriptionPlanRepository.deleteById(planId);
    }

    public VendorSubscriptionPlan enableSubscriptionPlan(Integer planId) {
        VendorSubscriptionPlan plan = getSubscriptionPlanById(planId);
        plan.setIsAvailable(true);
        return vendorSubscriptionPlanRepository.save(plan);
    }

    public VendorSubscriptionPlan disableSubscriptionPlan(Integer planId) {
        VendorSubscriptionPlan plan = getSubscriptionPlanById(planId);
        plan.setIsAvailable(false);
        return vendorSubscriptionPlanRepository.save(plan);
    }

    public List<VendorSubscriptionPlan> getEnabledSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findByIsAvailableTrue();
    }

    public List<VendorSubscriptionPlan> getDisabledSubscriptionPlans() {
        return vendorSubscriptionPlanRepository.findByIsAvailableFalse();
    }
    
    
    // filters
   

        public List<VendorSubscriptionPlan> getSubscriptionPlansByPriceRange(double minPrice, double maxPrice) {
            return vendorSubscriptionPlanRepository.findByPriceBetween(minPrice, maxPrice);
        }

        public List<VendorSubscriptionPlan> getSubscriptionPlansAbovePrice(double minPrice) {
            return vendorSubscriptionPlanRepository.findByPriceGreaterThan(minPrice);
        }
    
        public List<VendorSubscriptionPlan> getEnabledPlansByVendorId(Integer vendorId) {
            return vendorSubscriptionPlanRepository.findEnabledPlansByVendorId(vendorId);
        }
        
        public List<VendorSubscriptionPlan> getDisabledPlansByVendorId(Integer vendorId) {
            return vendorSubscriptionPlanRepository.findDisabledPlansByVendorId(vendorId);
        }
    
}
