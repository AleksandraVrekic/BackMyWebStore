package com.clothesShop.mypcg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.clothesShop.mypcg.auth.AuthenticationService;
import com.clothesShop.mypcg.dto.PaymentInfo;
import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.repository.OrderRepository;
import com.clothesShop.mypcg.service.OrderService;
import com.clothesShop.mypcg.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AuthenticationService authService;
    
    @Autowired 
    private PaymentService paymentService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)  // Convert Optional<Order> to ResponseEntity<Order>
                .orElseGet(() -> ResponseEntity.notFound().build());  // Handle case where Order is not found
    }


    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String tokenHeader,
                                             @RequestBody Order order) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isCustomer(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Order savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
    
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order updatedOrder = orderService.updateOrder(id, orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteOrder(@RequestHeader("Authorization") String tokenHeader, @PathVariable Long id) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the exception and return an appropriate response
            System.err.println("Error deleting order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/customer/{accountId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Order>> getCustomerOrders(@RequestHeader("Authorization") String tokenHeader, @PathVariable Integer accountId) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isCustomer(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Order> orders = orderService.getOrdersByAccountId(accountId);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping("/payment-intent")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> createPaymentIntent(@RequestHeader("Authorization") String tokenHeader,
                                                      @RequestBody PaymentInfo paymentInfo) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isCustomer(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfo);
            String paymentStr = paymentIntent.toJson();
            return new ResponseEntity<>(paymentStr, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}