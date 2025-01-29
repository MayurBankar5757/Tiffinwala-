package Tiffinwala.App.Dummy;

import com.fasterxml.jackson.annotation.JsonProperty;

import Tiffinwala.App.Enum.SubscriptionDuration;
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