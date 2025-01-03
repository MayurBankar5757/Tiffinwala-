package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.Feedback;
import Tiffinwala.App.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Optional<Feedback> getFeedbackById(Integer feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbacksByVendorSubscriptionPlan(Integer planId) {
        return feedbackRepository.findByVendorSubscriptionPlan_PlanId(planId);
    }

    public List<Feedback> getFeedbacksByUser(Integer userId) {
        return feedbackRepository.findByUser_Uid(userId);
    }

    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
