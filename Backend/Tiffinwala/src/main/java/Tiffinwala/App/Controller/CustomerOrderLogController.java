package Tiffinwala.App.Controller;

import Tiffinwala.App.Entities.CustomerOrderLog;
import Tiffinwala.App.Services.CustomerOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/orders")
public class CustomerOrderLogController {

    @Autowired
    private CustomerOrderLogService customerOrderLogService;

    @GetMapping
    public List<CustomerOrderLog> getAllOrders() {
        return customerOrderLogService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CustomerOrderLog> getOrderById(@PathVariable Integer orderId) {
        return customerOrderLogService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerOrderLog createOrder(@RequestBody CustomerOrderLog orderLog) {
        return customerOrderLogService.createOrder(orderLog);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<CustomerOrderLog> updateOrder(
            @PathVariable Integer orderId, 
            @RequestBody CustomerOrderLog updatedOrder) {
        try {
            return ResponseEntity.ok(customerOrderLogService.updateOrder(orderId, updatedOrder));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        customerOrderLogService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
