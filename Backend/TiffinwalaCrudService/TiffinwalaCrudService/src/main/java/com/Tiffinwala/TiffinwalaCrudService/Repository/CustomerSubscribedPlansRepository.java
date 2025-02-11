package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerSubscribedPlans;

import jakarta.transaction.Transactional;
@Transactional
@Repository
public interface CustomerSubscribedPlansRepository extends JpaRepository<CustomerSubscribedPlans, Integer> {

    Optional<CustomerSubscribedPlans> findByUserUid(Integer uid);

    List<CustomerSubscribedPlans> findByVendorSubscriptionPlanPlanId(Integer planId);
    
    public CustomerSubscribedPlans getById(Integer id);
    @Query("SELECT c FROM CustomerSubscribedPlans c WHERE c.user.uid = :userId AND c.endDate >= CURRENT_DATE")
    Optional<CustomerSubscribedPlans> getPlanByUid(Integer userId);
    
    List<CustomerSubscribedPlans> findByUser_Uid(Integer uid); // Fetch all plans for a specific user
    
    List<CustomerSubscribedPlans> findByEndDate(LocalDate endDate);


}
