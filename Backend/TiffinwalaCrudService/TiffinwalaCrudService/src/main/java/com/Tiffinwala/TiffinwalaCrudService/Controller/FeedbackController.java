package com.Tiffinwala.TiffinwalaCrudService.Controller;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Feedback;
import com.Tiffinwala.TiffinwalaCrudService.Services.FeedbackService;
import com.Tiffinwala.TiffinwalaCrudService.Dummy.FeedbackDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Create Feedback
    @PostMapping("feed/add")
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDummy feedbackDummy) {
        Feedback savedFeedback = feedbackService.saveFeedback(
                feedbackDummy.getUserId(),
                feedbackDummy.getVendorId(),
                feedbackDummy.getFeedbackText(),
                feedbackDummy.getRating()
        );
        return ResponseEntity.ok(savedFeedback);
    }

    // Get All Feedbacks
    @GetMapping("feed/all")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    // Get Feedbacks for a Specific Vendor
    @GetMapping("feed/vendor/{vendorId}")
    public ResponseEntity<List<Feedback>> getFeedbackByVendor(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByVendor(vendorId));
    }

    // Get Feedbacks by a Specific User
    @GetMapping("feed/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUser(userId));
    }
}
