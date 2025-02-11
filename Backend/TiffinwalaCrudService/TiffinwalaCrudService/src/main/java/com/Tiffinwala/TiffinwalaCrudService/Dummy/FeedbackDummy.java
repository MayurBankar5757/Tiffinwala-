package com.Tiffinwala.TiffinwalaCrudService.Dummy;

import lombok.Data;

@Data
public class FeedbackDummy {
    private Integer userId; // ID of the user giving feedback
    private Integer vendorId; // ID of the vendor receiving feedback
    private String feedbackText; // Feedback text
    private Integer rating; // Rating given (e.g., 1-5)
}
