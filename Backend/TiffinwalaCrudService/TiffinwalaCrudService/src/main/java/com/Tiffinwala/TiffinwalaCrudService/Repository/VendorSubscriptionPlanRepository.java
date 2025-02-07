package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.VendorSubscriptionPlan;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface VendorSubscriptionPlanRepository extends JpaRepository<VendorSubscriptionPlan, Integer> {

	
    List<VendorSubscriptionPlan> findByVendorVendorId(Integer vendorId);
    
    VendorSubscriptionPlan findByPlanId(Integer planId);
    @Modifying
    @Query("update VendorSubscriptionPlan set image = :file where planId = :id")
    int uploadPhoto(@Param("id") int id, @Param("file") byte[] file);

    
    List<VendorSubscriptionPlan> findByIsAvailableTrue();
    
    List<VendorSubscriptionPlan> findByIsAvailableFalse();
    
    @Query("SELECT v FROM VendorSubscriptionPlan v WHERE v.price BETWEEN :minPrice AND :maxPrice")
    List<VendorSubscriptionPlan> findByPriceBetween(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Query("SELECT v FROM VendorSubscriptionPlan v WHERE v.price > :minPrice")
    List<VendorSubscriptionPlan> findByPriceGreaterThan(@Param("minPrice") double minPrice);

    @Query(value = "SELECT * FROM vendor_subscription_plan WHERE vendor_id = :vendorId AND is_available = true", nativeQuery = true)
    List<VendorSubscriptionPlan> findEnabledPlansByVendorId(@Param("vendorId") Integer vendorId);
    
    @Query(value = "SELECT * FROM vendor_subscription_plan WHERE vendor_id = :vendorId AND is_available = false", nativeQuery = true)
    List<VendorSubscriptionPlan> findDisabledPlansByVendorId(@Param("vendorId") Integer vendorId);
}
