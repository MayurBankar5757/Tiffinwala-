package com.Tiffinwala.TiffinwalaCrudService.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    // Find feedbacks by vendor
    List<Feedback> findByVendorId(Integer vendorId);

    // Find feedbacks by user
    List<Feedback> findByUid(Integer uid);
    
    List<Feedback> findByRating(Integer rating);
    List<Feedback> findByRatingGreaterThan(Integer rating);
    
    List<Feedback> findByRatingGreaterThan(Integer rating, Sort sort);


}
