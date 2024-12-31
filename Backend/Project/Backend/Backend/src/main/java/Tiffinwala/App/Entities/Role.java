package Tiffinwala.App.Entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false, length = 50)
    private String roleName;
    
    // Default Constructor
    public Role() {}

    // Parameterized Constructor
    public Role(String roleName) {
        this.roleName = roleName;
    }
}
