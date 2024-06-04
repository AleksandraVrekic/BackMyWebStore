package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.dto.PaymentInfo;
import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.entity.Payment;
import com.clothesShop.mypcg.repository.OrderRepository;
import com.clothesShop.mypcg.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
  
    @Autowired
    private PaymentService paymentService;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        order.setOrderDate(orderDetails.getOrderDate());
        order.setOrderStatus(orderDetails.getOrderStatus());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setDiscount(orderDetails.getDiscount());
        order.setAccount(orderDetails.getAccount());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
    
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        return paymentService.createPaymentIntent(paymentInfo);
    }


    
}
