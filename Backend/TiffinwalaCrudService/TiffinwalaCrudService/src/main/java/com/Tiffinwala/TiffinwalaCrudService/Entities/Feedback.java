package com.Tiffinwala.TiffinwalaCrudService.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private User user; // Reference to User (who is giving feedback)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor; // Reference to Vendor (who is receiving feedback)

    @Column(nullable = false, length = 500)
    private String feedbackText; // Feedback content

    @Column(nullable = false)
    private Integer rating; // Rating given to the vendor (e.g., 1-5)

    @Column(nullable = false)
    private LocalDate feedbackDate; // Date when the feedback was given
}
