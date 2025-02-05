package com.Tiffinwala.TiffinwalaCrudService.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.FeedbackDto;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;
import com.Tiffinwala.TiffinwalaCrudService.Services.FeedbackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Create or update feedback
    @PostMapping
    public ResponseEntity<Feedback> saveFeedback(@RequestBody FeedbackDto feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }

    // Get feedback by ID
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer feedbackId) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(feedbackId);
        return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get feedbacks by vendor ID
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByVendorId(@PathVariable Integer vendorId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByVendorId(vendorId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Get feedbacks by user ID
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUid(@PathVariable Integer uid) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUid(uid);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Delete feedback by ID
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update feedback by ID
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Integer feedbackId, @RequestBody FeedbackDto feedbackDetails ) {
        Feedback updatedFeedback = feedbackService.updateFeedback(feedbackId, feedbackDetails);
        return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
    }
    
    // filter on >3
    @GetMapping("/high-ratings")
    public ResponseEntity<List<Feedback>> getFeedbacksWithHighRating() {
        List<Feedback> feedbacks = feedbackService.getFeedbacksWithHighRating();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    
    // filter on high to low
    @GetMapping("/high-ratings-sorted")
    public ResponseEntity<List<Feedback>> getFeedbacksWithHighRatingSorted() {
        List<Feedback> feedbacks = feedbackService.getFeedbacksWithHighRatingSorted();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    
    
}
