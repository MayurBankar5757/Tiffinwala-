package com.Tiffinwala.TiffinwalaAuthService.Entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
@Data
@Embeddable
public class Address {

    private String city;
    private String state;
    private String pincode;
    private String area;

    // Constructors
    public Address() {}

    public Address(String city, String state, String pincode, String area) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.area = area;
    }

    
}
