package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerSubscribedPlans;

import jakarta.transaction.Transactional;
@Transactional
@Repository
public interface CustomerSubscribedPlansRepository extends JpaRepository<CustomerSubscribedPlans, Integer> {

    Optional<CustomerSubscribedPlans> findByUserUid(Integer uid);

    List<CustomerSubscribedPlans> findByVendorSubscriptionPlanPlanId(Integer planId);
    
    public CustomerSubscribedPlans getById(Integer id);
    @Query(value = "SELECT * FROM customer_subscribed_plans WHERE uid = :userId AND v_subscription_id = :planId", nativeQuery = true)
    Optional<CustomerSubscribedPlans> getPlanByUidAndPlanId(@Param("userId") Integer userId, @Param("planId") Integer planId);
    List<CustomerSubscribedPlans> findByUser_Uid(Integer uid); // Fetch all plans for a specific user
    
    List<CustomerSubscribedPlans> findByEndDate(LocalDate endDate);


}
