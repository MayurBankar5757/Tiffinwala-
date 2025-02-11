package com.Tiffinwala.TiffinwalaCrudService.Services;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaCrudService.Repository.FeedbackRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.UserRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    // Save Feedback
    public Feedback saveFeedback(Integer uid, Integer vendorId, String feedbackText, Integer rating) {
        Optional<User> user = userRepository.findById(uid);
        Optional<Vendor> vendor = vendorRepository.findById(vendorId);

        if (user.isEmpty() || vendor.isEmpty()) {
            throw new IllegalArgumentException("User or Vendor not found");
        }

        Feedback feedback = new Feedback();
        feedback.setUser(user.get());
        feedback.setVendor(vendor.get());
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setFeedbackDate(LocalDate.now());

        return feedbackRepository.save(feedback);
    }

    // Get All Feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Get Feedbacks for a Vendor
    public List<Feedback> getFeedbackByVendor(Integer vendorId) {
        return feedbackRepository.findByVendorVendorId(vendorId);
    }

    // Get Feedbacks given by a User
    public List<Feedback> getFeedbackByUser(Integer userId) {
        return feedbackRepository.findByUserUid(userId);
    }
}
