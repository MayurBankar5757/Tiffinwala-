package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_plan_id", nullable = false)
    private CustomerSubscribedPlans customerSubscribedPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "V_Subscription_id", nullable = false)
    private VendorSubscriptionPlan vendorSubscriptionPlan;

    @Column(name = "Feedback_Text", nullable = false, length = 500)
    private String feedbackText;

    @Column(nullable = false)
    private Integer rating;



    // Constructors
    public Feedback() {}

    public Feedback(User user, CustomerSubscribedPlans customerPlan, VendorSubscriptionPlan vendorSubscriptionPlan, String feedbackText, Integer rating, LocalDate feedbackDate) {
        this.user = user;
        this.customerSubscribedPlan = customerPlan;
        this.vendorSubscriptionPlan = vendorSubscriptionPlan;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    
}
