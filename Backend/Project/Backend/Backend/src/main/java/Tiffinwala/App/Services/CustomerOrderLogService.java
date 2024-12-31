package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.CustomerOrderLog;
import Tiffinwala.App.Repository.CustomerOrderLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderLogService {

    @Autowired
    private CustomerOrderLogRepository customerOrderLogRepository;

    public List<CustomerOrderLog> getAllOrders() {
        return customerOrderLogRepository.findAll();
    }

    public Optional<CustomerOrderLog> getOrderById(Integer orderId) {
        return customerOrderLogRepository.findById(orderId);
    }

    public CustomerOrderLog createOrder(CustomerOrderLog orderLog) {
        return customerOrderLogRepository.save(orderLog);
    }

    public CustomerOrderLog updateOrder(Integer orderId, CustomerOrderLog updatedOrder) {
        return customerOrderLogRepository.findById(orderId)
                .map(order -> {
                    order.setQuantity(updatedOrder.getQuantity());
                    order.setDateTime(updatedOrder.getDateTime());
                    order.setUser(updatedOrder.getUser());
                    return customerOrderLogRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }

    public void deleteOrder(Integer orderId) {
        customerOrderLogRepository.deleteById(orderId);
    }
}
