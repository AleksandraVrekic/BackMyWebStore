package com.clothesShop.mypcg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.dto.CustomerRegistrationDto;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationDto registrationDto) {
        try {
            Account account = registrationService.registerNewCustomer(registrationDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
