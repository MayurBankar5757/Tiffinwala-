package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Tiffin")
public class Tiffin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tiffinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "V_Subscription_id", nullable = false)
    private VendorSubscriptionPlan vendorSubscriptionPlan;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "Food_type", nullable = false)
    private String foodType;

    @Column(nullable = false)
    private String description;

    @Column
    private String image;

    // Constructors
    public Tiffin() {}

    public Tiffin(VendorSubscriptionPlan vendorSubscriptionPlan, String day, Integer price, String foodType, String description, String image) {
        this.vendorSubscriptionPlan = vendorSubscriptionPlan;
        this.day = day;
        this.price = price;
        this.foodType = foodType;
        this.description = description;
        this.image = image;
    }

   
}
