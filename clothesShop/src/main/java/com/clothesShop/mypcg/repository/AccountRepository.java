package com.clothesShop.mypcg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothesShop.mypcg.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findByUserName(String userName);
	Account findByuserName(String userName);
	boolean existsByuserName(String userName);
	boolean existsByEmail(String email);
    // Dodaj metodu za pronalaženje naloga po email adresi
    Optional<Account> findByEmail(String email);

}

