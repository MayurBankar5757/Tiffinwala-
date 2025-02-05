package com.Tiffinwala.TiffinwalaCrudService.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "user_id") // Assuming 'user_id' is the primary key of the 'User' table
    private User user; // Reference to the User entity

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor; // Reference to the Vendor entity

    private String feedbackText;
    
    private int rating;
    
    private LocalDate feedbackDate;

    // Getters and Setters
}
