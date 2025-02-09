package com.Tiffinwala.TiffinwalaCrudService.Dummy;


public class FeedbackDto {

    private int uid;  // Foreign key referencing the User
    private int vendorId;  // Foreign key referencing the Vendor
    private String feedbackText;
    private int rating;
  

    // Default constructor
    public FeedbackDto() {}

    // Constructor with all fields
    public FeedbackDto(int uid, int vendorId, String feedbackText, int rating) {
        this.uid = uid;
        this.vendorId = vendorId;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    // Getters and setters
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    
}
