package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByVendorSubscriptionPlan_PlanId(Integer planId);
    List<Feedback> findByUser_Uid(Integer userId);
}
