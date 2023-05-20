package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.entity.OrderDetail;
import com.clothesShop.mypcg.exception.OrderDetailNotFoundException;
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


    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        // Retrieve the existing order detail
        Optional<OrderDetail> orderDetailOptional = getOrderDetailsById(orderDetail.getId());
        if (orderDetailOptional.isPresent()) {
            OrderDetail existingOrderDetail = orderDetailOptional.get();
            // Update the properties of the existing order detail
            existingOrderDetail.setAmount(orderDetail.getAmount());
            existingOrderDetail.setPrice(orderDetail.getPrice());
            existingOrderDetail.setQuantity(orderDetail.getQuantity());

            // Save and return the updated order detail
            return orderDetailsRepository.save(existingOrderDetail);
        } else {
            throw new OrderDetailNotFoundException("Order detail not found for id: " + orderDetail.getId());
        }
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

