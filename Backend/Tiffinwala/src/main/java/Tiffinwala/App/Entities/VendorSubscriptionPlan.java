package Tiffinwala.App.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Tiffinwala.App.Enum.SubscriptionDuration;
import jakarta.persistence.*;
import lombok.Data;
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "vendor_subscription_plan")
public class VendorSubscriptionPlan {

	//	Plan_id, Vendor_id, Name, Price, Description, Image, is_available
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Plan_id")
	    private Integer planId;
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "Vendor_id", nullable = false)
	    private Vendor vendor;  // Assuming Vendor entity exists

	    @Column(name = "Name", nullable = false)
	    private String name;

	    @Column(name = "Price", nullable = false)
	    private Integer price;

	    @Column(name = "Description", nullable = false)
	    private String description;

	    @Lob
	    @Column(name = "image", columnDefinition = "LONGBLOB")
	    private byte[] image;


	    @Column(name = "is_available", nullable = false)
	    private Boolean isAvailable;
	    
	    @Enumerated(EnumType.STRING)  // ✅ Store ENUM as String in DB
	    @Column(name = "duration", nullable = false)
	    private SubscriptionDuration duration;  // ✅ Using Enum

	    public VendorSubscriptionPlan(Vendor vendor, String name, Integer price, String description,
                Boolean isAvailable, SubscriptionDuration duration) {
	    		this.vendor = vendor;
	    		this.name = name;
	    		this.price = price;
	    		this.description = description;
	    		this.isAvailable = isAvailable;
	    		this.duration = duration;
	    	}

	    public VendorSubscriptionPlan() {}
   
   
		}
