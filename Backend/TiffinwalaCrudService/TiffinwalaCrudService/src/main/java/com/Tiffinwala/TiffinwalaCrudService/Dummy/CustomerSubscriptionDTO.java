package com.Tiffinwala.TiffinwalaCrudService.Dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerSubscriptionDTO {
    private Integer userId;
    private Integer subscriptionPlanId;
    @NotNull
    private LocalDate startDate; // Add this field
}