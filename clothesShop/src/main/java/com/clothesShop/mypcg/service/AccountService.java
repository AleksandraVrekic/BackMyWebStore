package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }


    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(int id, Account updatedAccount) {
        Optional<Account> existingAccountOptional = accountRepository.findById(id);
        if (existingAccountOptional.isPresent()) {
            Account existingAccount = existingAccountOptional.get();
            existingAccount.setUserName(updatedAccount.getUserName());
            existingAccount.setPassword(updatedAccount.getPassword());
            existingAccount.setUserRole(updatedAccount.getUserRole());
            return accountRepository.save(existingAccount);
        }
        return null;
    }

    public boolean deleteAccount(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            accountRepository.delete(accountOptional.get());
            return true;
        }
        return false;
    }
}

