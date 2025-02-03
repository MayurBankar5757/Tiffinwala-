package com.Tiffinwala.TiffinwalaCrudService.Dummy;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Tiffin;

public class TiffinDTO {
    private Integer tiffinId;
    private String day;
    private String name;
    private String foodType;
    private String description;
    private Integer planId;  // Store only the plan ID, not the entire object

    public TiffinDTO(Tiffin tiffin) {
        this.tiffinId = tiffin.getTiffinId();
        this.day = tiffin.getDay();
        this.name = tiffin.getName();
        this.foodType = tiffin.getFoodType();
        this.description = tiffin.getDescription();
        this.planId = tiffin.getVendorSubscriptionPlan().getPlanId();
    }

    // Getters and Setters
    public Integer getTiffinId() {
        return tiffinId;
    }

    public void setTiffinId(Integer tiffinId) {
        this.tiffinId = tiffinId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
