package com.Tiffinwala.TiffinwalaCrudService.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByVendorVendorId(Integer vendorId); // Fetch feedbacks for a vendor
    List<Feedback> findByUserUid(Integer userId); // Fetch feedbacks given by a user
}
