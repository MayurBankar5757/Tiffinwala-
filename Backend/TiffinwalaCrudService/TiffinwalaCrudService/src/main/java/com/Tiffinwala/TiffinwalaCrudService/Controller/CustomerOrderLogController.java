package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerOrderLog;
import com.Tiffinwala.TiffinwalaCrudService.Services.CustomerOrderLogService;

@RestController
@RequestMapping("/api2")
//@CrossOrigin(origins = "http://localhost:3010") 
public class CustomerOrderLogController {

    @Autowired
    private CustomerOrderLogService customerOrderLogService;

    @GetMapping
    public List<CustomerOrderLog> getAllOrders() {
        return customerOrderLogService.getAllOrders();
    }

    @GetMapping("/col/{orderId}")
    public ResponseEntity<CustomerOrderLog> getOrderById(@PathVariable Integer orderId) {
        return customerOrderLogService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerOrderLog createOrder(@RequestBody CustomerOrderLog orderLog) {
        return customerOrderLogService.createOrder(orderLog);
    }

    @PutMapping("/col/{orderId}")
    public ResponseEntity<CustomerOrderLog> updateOrder(
            @PathVariable Integer orderId, 
            @RequestBody CustomerOrderLog updatedOrder) {
        try {
            return ResponseEntity.ok(customerOrderLogService.updateOrder(orderId, updatedOrder));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/col/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        customerOrderLogService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
