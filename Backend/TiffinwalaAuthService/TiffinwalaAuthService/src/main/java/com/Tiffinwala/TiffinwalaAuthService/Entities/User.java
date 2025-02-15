package com.Tiffinwala.TiffinwalaAuthService.Entities;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(nullable = false, length = 50)
    private String fname;

    @Column(nullable = false, length = 50)
    private String lname;

    @Column(nullable = false, unique = true, length = 70)
    private String email;

    @ManyToOne
    @JoinColumn(name = "Rid")
    private Role role;

    @Embedded
    private Address address;

    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String contact;

    // Constructors
    public User() {}

    public User(String fname, String lname, String email, Role role, Address address, String password, String contact) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
        this.address = address;
        this.password = password;
        this.contact = contact;
    }
}
