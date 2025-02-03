package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerSubscribedPlans;

import jakarta.transaction.Transactional;
@Transactional
@Repository
public interface CustomerSubscribedPlansRepository extends JpaRepository<CustomerSubscribedPlans, Integer> {

    Optional<CustomerSubscribedPlans> findByUserUid(Integer uid);

    List<CustomerSubscribedPlans> findByVendorSubscriptionPlanPlanId(Integer planId);
    
    public CustomerSubscribedPlans getById(Integer id);
    
    
    List<CustomerSubscribedPlans> findByUser_Uid(Integer uid); // Fetch all plans for a specific user

}
