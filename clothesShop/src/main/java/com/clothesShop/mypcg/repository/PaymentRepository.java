package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>,PagingAndSortingRepository<Payment, Integer> {

	Payment findPaymentByOrder(Order order);
}