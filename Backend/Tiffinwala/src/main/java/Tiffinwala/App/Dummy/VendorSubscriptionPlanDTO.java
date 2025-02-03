package Tiffinwala.App.Dummy;

import com.fasterxml.jackson.annotation.JsonProperty;
import Tiffinwala.App.Enum.SubscriptionDuration;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VendorSubscriptionPlanDTO {

    private Integer vendorId;
    private String name;
    private Integer price;
    private String description;
    private boolean isAvaliable;
    private SubscriptionDuration duration;  // ✅ Use Enum
    private MultipartFile image;  // Add this field for image upload
}