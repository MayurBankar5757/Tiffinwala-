package Tiffinwala.App.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "Customer_Order_Log")
public class CustomerOrderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uid", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "Date_Time", nullable = false)
    private LocalDateTime dateTime;

    // Constructors
    public CustomerOrderLog() {}

    public CustomerOrderLog(User user, Integer quantity, LocalDateTime dateTime) {
        this.user = user;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

}
