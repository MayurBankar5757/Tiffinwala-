package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "vendor_subscription_plan")
public class VendorSubscriptionPlan {

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

	    @Column(name = "Image")
	    private String image;

	    @Column(name = "is_available", nullable = false)
	    private Boolean isAvailable;

    // Constructors
    public VendorSubscriptionPlan() {}

    public VendorSubscriptionPlan(Vendor vendor, String name, Integer price, String description, String image, Boolean isAvailable) {
        this.vendor = vendor;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.isAvailable = isAvailable;
    }

   
}
