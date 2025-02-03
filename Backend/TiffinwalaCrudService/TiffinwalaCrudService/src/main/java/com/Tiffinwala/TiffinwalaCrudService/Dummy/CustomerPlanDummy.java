package com.Tiffinwala.TiffinwalaCrudService.Dummy;
import java.time.LocalDate;


import lombok.Data;


@Data
public class CustomerPlanDummy {
    
    private int customerPlanId; // Unique identifier for the customer's plan
    private int userId;         // Unique identifier for the user (Uid)
    private int subscriptionId; // Vendor subscription ID (v_subscription_id)
    private LocalDate startDate; // Plan start date
    private LocalDate endDate;   // Plan end date
    private LocalDate orderedDate; // Plan ordered date

    // Default constructor
    public CustomerPlanDummy() {
    }

    // Parameterized constructor
    public CustomerPlanDummy(int customerPlanId, int userId, int subscriptionId, LocalDate startDate, LocalDate endDate, LocalDate orderedDate) {
        this.customerPlanId = customerPlanId;
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderedDate = orderedDate;
    }

}
