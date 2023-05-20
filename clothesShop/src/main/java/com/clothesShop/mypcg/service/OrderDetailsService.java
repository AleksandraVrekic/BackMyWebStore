package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.entity.OrderDetail;
import com.clothesShop.mypcg.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailRepository orderDetailsRepository;

    @Autowired
    public OrderDetailsService(OrderDetailRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderDetail> getOrderDetailsById(int id) {
        return orderDetailsRepository.findById(id);
    }

    public OrderDetail createOrderDetails(OrderDetail orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetail updateOrderDetails(int id, OrderDetail updatedOrderDetails) {
        Optional<OrderDetail> existingOrderDetailsOptional = orderDetailsRepository.findById(id);
        if (existingOrderDetailsOptional.isPresent()) {
            OrderDetail existingOrderDetails = existingOrderDetailsOptional.get();
            existingOrderDetails.setAmount(updatedOrderDetails.getAmount());
            existingOrderDetails.setPrice(updatedOrderDetails.getPrice());
            existingOrderDetails.setQuantity(updatedOrderDetails.getQuantity());
            // Update other properties as needed
            return orderDetailsRepository.save(existingOrderDetails);
        }
        return null;
    }

    public boolean deleteOrderDetails(int id) {
        Optional<OrderDetail> orderDetailsOptional = orderDetailsRepository.findById(id);
        if (orderDetailsOptional.isPresent()) {
            orderDetailsRepository.delete(orderDetailsOptional.get());
            return true;
        }
        return false;
    }
}

