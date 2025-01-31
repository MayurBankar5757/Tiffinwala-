package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.VendorSubscriptionPlan;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    


}
