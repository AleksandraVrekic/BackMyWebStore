package com.clothesShop.mypcg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String productName);
    // Method to find products by category ID
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('% ', :productName, '%')) OR LOWER(p.name) LIKE LOWER(CONCAT(:productName, '%'))")
    List<Product> findByProductNameStartingWith(String productName);
    List<Product> findByCategoryCategoryId(Long categoryId);
}

