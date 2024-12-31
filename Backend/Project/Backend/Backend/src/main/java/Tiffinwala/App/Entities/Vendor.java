package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vendorId;

    @Column(nullable = false)
    private Boolean isVerified;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VendorSubscriptionPlan> subscriptionPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid", nullable = false)
    private User user;  // Assuming User is an entity that has been defined already

    // Constructors
    public Vendor() {}

    public Vendor(Boolean isVerified, User user) {
        this.isVerified = isVerified;
        this.user = user;
    }

}


