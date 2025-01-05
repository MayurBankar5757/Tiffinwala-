package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Tiffin")
public class Tiffin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Tiffin_id")
    private Integer tiffinId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "V_Subscription_id", nullable = false)
    private VendorSubscriptionPlan vendorSubscriptionPlan;

    @Column(nullable = false)
    private String day;
    
    @Column(name ="Tiffin_name")
    private String name;

    @Column(name = "food_type", nullable = false)
    private String foodType;

    @Column(nullable = false)
    private String description;

    //Tiffin_id, V_Subscription_id, Tiffin_name, day, description, food_type
  
    // Constructors
    public Tiffin() {}

    public Tiffin(VendorSubscriptionPlan vendorSubscriptionPlan, String day, String foodType, String description ) {
        this.vendorSubscriptionPlan = vendorSubscriptionPlan;
        this.day = day;
        
        this.foodType = foodType;
        this.description = description;
       
    }

   
}
