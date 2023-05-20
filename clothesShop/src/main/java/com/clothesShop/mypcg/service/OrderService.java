package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(int id, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setAmount(updatedOrder.getAmount());
            existingOrder.setCustomerAddress(updatedOrder.getCustomerAddress());
            existingOrder.setCustomerEmail(updatedOrder.getCustomerEmail());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setCustomerPhone(updatedOrder.getCustomerPhone());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setOrderNum(updatedOrder.getOrderNum());
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    public boolean deleteOrder(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return true;
        }
        return false;
    }
}
