package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Dummy.VendorSubscriptionPlanDTO;
import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import Tiffinwala.App.Repository.VendorRepository;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Exceptions.ConflictException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

        // Handle image upload
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                subscriptionPlan.setImage(dto.getImage().getBytes());  // Convert MultipartFile to byte[]
            } catch (IOException e) {
                throw new RuntimeException("Failed to process the image file.", e);
            }
        }

        try {
            return vendorSubscriptionPlanRepository.save(subscriptionPlan);
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof ConstraintViolationException) {
                ConstraintViolationException constraintException = (ConstraintViolationException) rootCause;
                String constraintName = constraintException.getConstraintName();
                if (constraintName.contains("unique_vendor_plan_name")) {
                    throw new ConflictException("A subscription plan with the same name already exists for this vendor.");
                } else if (constraintName.contains("unique_vendor_duration")) {
                    throw new ConflictException("A subscription plan with the same duration already exists for this vendor.");
                } else {
                    throw new ConflictException("Duplicate entry detected. Please ensure all fields are unique.");
                }
            } else {
                throw new ConflictException("Duplicate entry detected. Please ensure all fields are unique.");
            }
        }
    }

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
    
}
