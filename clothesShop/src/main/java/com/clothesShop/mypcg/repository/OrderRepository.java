package com.clothesShop.mypcg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Dodatne metode za pretragu po potrebi
	 List<Order> findByAccount_Id(Integer accountId);
	
}
