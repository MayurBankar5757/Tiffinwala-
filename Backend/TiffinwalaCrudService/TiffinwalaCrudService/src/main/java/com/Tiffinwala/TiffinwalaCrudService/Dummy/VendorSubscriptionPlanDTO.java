package com.Tiffinwala.TiffinwalaCrudService.Dummy;

import com.Tiffinwala.TiffinwalaCrudService.Enum.SubscriptionDuration;


import lombok.Data;

@Data
public class VendorSubscriptionPlanDTO {
    
    private Integer vendorId;
    private String name;
    private Integer price;
    private String description;
   
    private boolean isAvaliable;
   private SubscriptionDuration duration;  // ✅ Use Enum
	
}