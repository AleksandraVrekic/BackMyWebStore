package com.clothesShop.mypcg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.dto.CustomerRegistrationDto;
import com.clothesShop.mypcg.dto.StaffRegistrationDto;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Customer;
import com.clothesShop.mypcg.entity.Staff;
import com.clothesShop.mypcg.service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationDto registrationDto) {
        try {
            Customer customer = registrationService.registerNewCustomer(registrationDto);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/register/staff")
    public ResponseEntity<?> registerStaff(@RequestBody StaffRegistrationDto registrationDto) {
        try {
            Staff staff = registrationService.registerNewStaff(registrationDto);
            return new ResponseEntity<>(staff, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
