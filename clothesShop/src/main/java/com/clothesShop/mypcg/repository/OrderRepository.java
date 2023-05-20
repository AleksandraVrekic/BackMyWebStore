package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // You can define custom query methods here if needed
}

