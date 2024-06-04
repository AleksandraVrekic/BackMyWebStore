package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.dto.CustomerRegistrationDto;
import com.clothesShop.mypcg.dto.CustomerUpdateDto;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Customer;
import com.clothesShop.mypcg.entity.Role;
import com.clothesShop.mypcg.entity.Address;
import com.clothesShop.mypcg.repository.AccountRepository;
import com.clothesShop.mypcg.repository.AddressRepository;
import com.clothesShop.mypcg.repository.CustomerRepository;

@Service
public class RegistrationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public Account registerNewCustomer(CustomerRegistrationDto dto) throws Exception {
        if (accountRepository.existsByuserName(dto.getUsername())) {
            throw new Exception("Username already exists!");
        }

        Account newAccount = new Account();
        newAccount.setUserName(dto.getUsername());
        newAccount.setPassword(dto.getPassword()); 
        newAccount.setFirstName(dto.getName());
        newAccount.setLastName(dto.getSurname());
        newAccount.setEmail(dto.getEmail());
        newAccount.setUserRole(Role.CUSTOMER); // Assuming ERole is an enum
        accountRepository.save(newAccount);

        Customer newCustomer = new Customer();
        newCustomer.setAccount(newAccount);
        newCustomer.setPhone(dto.getPhone());
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new Exception("Address not found"));
        newCustomer.setAddress(address);
        customerRepository.save(newCustomer);

        return newAccount;
    }
    
    public Customer updateCustomer(Integer customerId, CustomerUpdateDto dto) throws Exception {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new Exception("Customer not found"));

        customer.setPhone(dto.getPhone());

        // Update Address
        Address address = customer.getAddress();
        if (address != null && dto.getAddress() != null) {
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setCountry(dto.getAddress().getCountry());
            address.setZip(dto.getAddress().getZip());
            addressRepository.save(address);
        }

        // Update Account
        Account account = customer.getAccount();
        if (account != null && dto.getAccount() != null) {
            account.setUserName(dto.getAccount().getUserName());
            account.setFirstName(dto.getAccount().getFirstName());
            account.setLastName(dto.getAccount().getLastName());
            account.setEmail(dto.getAccount().getEmail());
            // Consider security implications of updating password
            accountRepository.save(account);
        }

        customerRepository.save(customer);
        return customer;
    }


}

