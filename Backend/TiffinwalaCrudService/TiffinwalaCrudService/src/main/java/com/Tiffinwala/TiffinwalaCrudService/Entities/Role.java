package com.Tiffinwala.TiffinwalaCrudService.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Role_Id")
    private Integer roleId;

    @Column(name = "role_name",nullable = false, length = 50)
    private String roleName;
    
    // Default Constructor
    public Role() {}

    // Parameterized Constructor
    public Role(String roleName) {
        this.roleName = roleName;
    }
}