package com.clothesShop.mypcg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    // Možete dodati dodatne metode za prilagođene upite
}