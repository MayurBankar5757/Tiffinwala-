package com.Tiffinwala.TiffinwalaCrudService.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Customer_Subscribed_Plans")
public class CustomerSubscribedPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_plan_id")
    private Integer customerPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid", nullable = false)
    private User user;  // Assuming the User entity exists and is used
    
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_Subscription_id", nullable = false)
    private VendorSubscriptionPlan vendorSubscriptionPlan;  // Assuming VendorSubscriptionPlan entity exists

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "ordered_date")
    private LocalDate orderedDate;

    // Constructors
    public CustomerSubscribedPlans() {}

    public CustomerSubscribedPlans(User user, VendorSubscriptionPlan vendorSubscriptionPlan, LocalDate startDate, LocalDate endDate, LocalDate orderedDate) {
        this.user = user;
        this.vendorSubscriptionPlan = vendorSubscriptionPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderedDate = orderedDate;
    }
}
