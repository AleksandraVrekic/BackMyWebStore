package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
