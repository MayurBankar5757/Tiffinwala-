package com.Tiffinwala.TiffinwalaAuthService.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @Column(name="Vendor_id")
    private Integer vendorId;

   // @Column(name = "Is_verified" ,nullable = false)
    private Boolean isVerified;
   
    private String foodLicenceNo;
  
    private String adhar_no;
//
//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<VendorSubscriptionPlan> subscriptionPlans;

    @OneToOne(fetch = FetchType.EAGER)  // Change to EAGER
    @JoinColumn(name = "uid", nullable = false)
    private User user;
  // Assuming User is an entity that has been defined already

    // Constructors
 
}

//Lazy Loading (fetch = FetchType.LAZY):
//
//The associated entity is not loaded when the parent entity is fetched. Instead, it's loaded only when it's explicitly accessed (e.g., if you access the user field in the Vendor entity).
//This is the default behavior for many-to-one and one-to-many relationships.
//Eager Loading (fetch = FetchType.EAGER):
//
//The associated entity is loaded along with the parent entity, meaning that related entities are fetched immediately.
//
//
