package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorSubscriptionPlanRepository extends JpaRepository<VendorSubscriptionPlan, Integer> {

    List<VendorSubscriptionPlan> findByVendorVendorId(Integer vendorId);
    
    VendorSubscriptionPlan findByPlanId(Integer planId);
}
