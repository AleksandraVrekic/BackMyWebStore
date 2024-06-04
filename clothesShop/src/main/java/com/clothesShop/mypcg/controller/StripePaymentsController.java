package com.clothesShop.mypcg.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.entity.StripePayment;
import com.clothesShop.mypcg.repository.StripePaymentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stripe")
public class StripePaymentsController {

    @Autowired
    private StripePaymentRepository repository;

    @GetMapping("/payment")
    public Collection<StripePayment> getAllPayment(){
        return repository.findAll();
    }
}