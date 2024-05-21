package com.clothesShop.mypcg.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	//List<Customer> findAll();
	//Optional<Customer> findById(Integer id);
	Customer save(Customer customer);
    boolean existsById(Integer id);
    void deleteById(Integer id);
}
