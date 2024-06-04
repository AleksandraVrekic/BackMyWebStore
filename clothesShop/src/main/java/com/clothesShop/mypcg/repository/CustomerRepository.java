package com.clothesShop.mypcg.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	//List<Customer> findAll();
	//Optional<Customer> findById(Integer id);
	Customer save(Customer customer);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    @Query("SELECT c FROM Customer c JOIN FETCH c.account a WHERE a.userName = :username")
    Optional<Customer> findCustomerWithAccountByUsername(@PathVariable String username);
}
