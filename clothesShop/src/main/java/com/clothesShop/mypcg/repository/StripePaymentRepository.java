package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.StripePayment;

@Repository
public interface StripePaymentRepository extends JpaRepository<StripePayment, Integer> {
}