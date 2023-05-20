package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // You can define custom query methods here if needed
}

