package com.Tiffinwala.TiffinwalaAuthService.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Role_Id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }

    // Default Constructor
    public Role() {}

    // Parameterized Constructor
    public Role(String roleName) {
        this.roleName = roleName;
    }
}
