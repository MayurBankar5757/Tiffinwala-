package com.Tiffinwala.TiffinwalaCrudService.Services;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.FeedbackDto;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;
import com.Tiffinwala.TiffinwalaCrudService.Entities.User;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Vendor;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Repository.FeedbackRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.UserRepository;
import com.Tiffinwala.TiffinwalaCrudService.Repository.VendorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private VendorRepository vrepo;
    
    @Autowired
    private UserRepository urepo;

    // Create or update feedback
    public Feedback saveFeedback(FeedbackDto feedback) {
    	Feedback feed = new Feedback();
    	Vendor v = new Vendor();
    	v = vrepo.findById(feedback.getVendorId())
    			.orElseThrow( () -> new ResourceNotFoundException("vendor not found" + feedback.getVendorId()));
    	
    	User u = new User();
    	u = urepo.findById(feedback.getUid())
    			.orElseThrow(() -> new ResourceNotFoundException("User not found"+ feedback.getUid()));
    	
    	feed.setFeedbackDate(LocalDate.now());
    	feed.setRating(feedback.getRating());
    	feed.setFeedbackText(feedback.getFeedbackText());
    	feed.setUser(u);
    	feed.setVendor(v);
    	
    	
    	
        return feedbackRepository.save(feed);
    }

    // Get feedback by ID
    public Optional<Feedback> getFeedbackById(Integer feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    // Get feedbacks by vendor ID
    public List<Feedback> getFeedbacksByVendorId(Integer vendorId) {
        return feedbackRepository.findByVendorId(vendorId);
    }

    // Get feedbacks by user ID
    public List<Feedback> getFeedbacksByUid(Integer uid) {
        return feedbackRepository.findByUid(uid);
    }

    // Delete feedback by ID
    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    // Update feedback
    public Feedback updateFeedback(Integer feedbackId, FeedbackDto feedback) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId).orElseThrow(()-> new ResourceNotFoundException("existing feedback not found"));
        existingFeedback.setFeedbackText(feedback.getFeedbackText());
        existingFeedback.setRating(feedback.getRating());
        existingFeedback.setFeedbackDate(LocalDate.now());// updated date
        return feedbackRepository.save(existingFeedback);
    }
    
    // filter > 3
    
    public List<Feedback> getFeedbacksWithHighRating() {
        return feedbackRepository.findByRatingGreaterThan(3); // Fetch feedbacks with rating > 3
    }
    
    public List<Feedback> getFeedbacksWithHighRatingSorted() {
        return feedbackRepository.findByRatingGreaterThan(3, Sort.by(Sort.Direction.DESC, "rating"));
    }
}

