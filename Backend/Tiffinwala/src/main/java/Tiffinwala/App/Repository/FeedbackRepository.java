package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByVendorSubscriptionPlan_PlanId(Integer planId);
    List<Feedback> findByUser_Uid(Integer userId);
}
