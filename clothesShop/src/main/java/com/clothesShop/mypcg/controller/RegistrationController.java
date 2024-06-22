package com.clothesShop.mypcg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.auth.AuthenticationService;
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
    
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationDto registrationDto) {
        try {
            Customer customer = registrationService.registerNewCustomer(registrationDto);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equals("Username already exists!") || e.getMessage().equals("Email already exists!")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/register/staff")
    public ResponseEntity<?> registerStaff(@RequestHeader("Authorization") String tokenHeader, @RequestBody StaffRegistrationDto registrationDto) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix

        if (!authService.isSuperAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Staff staff = registrationService.registerNewStaff(registrationDto);
            return new ResponseEntity<>(staff, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equals("Username already exists!") || e.getMessage().equals("Email already exists!")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
