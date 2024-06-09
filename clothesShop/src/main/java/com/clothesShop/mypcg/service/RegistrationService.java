package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.dto.CustomerRegistrationDto;
import com.clothesShop.mypcg.dto.CustomerUpdateDto;
import com.clothesShop.mypcg.dto.StaffRegistrationDto;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Customer;
import com.clothesShop.mypcg.entity.Role;
import com.clothesShop.mypcg.entity.Staff;
import com.clothesShop.mypcg.entity.Address;
import com.clothesShop.mypcg.repository.AccountRepository;
import com.clothesShop.mypcg.repository.AddressRepository;
import com.clothesShop.mypcg.repository.CustomerRepository;
import com.clothesShop.mypcg.repository.StaffRepository;

@Service
public class RegistrationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
   
    @Autowired
    private StaffRepository staffRepository;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public Customer registerNewCustomer(CustomerRegistrationDto dto) throws Exception {
        if (accountRepository.existsByuserName(dto.getUsername())) {
            throw new Exception("Username already exists!");
        }

        if (accountRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already exists!");
        }

        Account newAccount = new Account();
        newAccount.setUserName(dto.getUsername());
        newAccount.setPassword(dto.getPassword());
        newAccount.setFirstName(dto.getName());
        newAccount.setLastName(dto.getSurname());
        newAccount.setEmail(dto.getEmail());
        newAccount.setUserRole(Role.CUSTOMER); // Assuming Role is an enum
        accountRepository.save(newAccount);

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setZip(dto.getZip());
        addressRepository.save(address);

        Customer newCustomer = new Customer();
        newCustomer.setAccount(newAccount);
        newCustomer.setPhone(dto.getPhone());
        newCustomer.setAddress(address);
        customerRepository.save(newCustomer);

        return newCustomer;
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
    
    public Staff registerNewStaff(StaffRegistrationDto dto) throws Exception {
        if (accountRepository.existsByuserName(dto.getUserName())) {
            throw new Exception("Username already exists!");
        }

        Account newAccount = new Account();
        newAccount.setUserName(dto.getUserName());
        newAccount.setPassword(dto.getPassword()); 
        newAccount.setFirstName(dto.getName());
        newAccount.setLastName(dto.getSurname());
        newAccount.setEmail(dto.getEmail());
        newAccount.setUserRole(Role.ADMIN); // Assuming ERole is an enum
        accountRepository.save(newAccount);

        Staff newStaff = new Staff();
        newStaff.setPosition(dto.getPosition());
        newStaff.setAccount(newAccount);
        staffRepository.save(newStaff);

        return newStaff;
    }


}

