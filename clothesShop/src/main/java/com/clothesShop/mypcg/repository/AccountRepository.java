package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // You can define custom query methods here if needed
}
