package com.Tiffinwala.TiffinwalaCrudService.Dummy;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Vendor_Sub_Plan_Dummy {
    private Integer vid;
    private String name;
    private String description;
    private Integer price;

    @JsonProperty("isAvaliable") // Maps the JSON key "isAvaliable" to this field
    private boolean isAvaliable;
}