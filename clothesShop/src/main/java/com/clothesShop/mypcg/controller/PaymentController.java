package com.clothesShop.mypcg.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.entity.Payment;
import com.clothesShop.mypcg.exception.ResourceNotFoundException;
import com.clothesShop.mypcg.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
    private PaymentRepository paymentRepository;

	@Autowired
	private StripeClient stripeClient;
	
	@GetMapping("/payment")
    public Collection<Payment> getAllPayment(){
		return paymentRepository.findAll();}
    

    @GetMapping("/payment/{id}")
    public Payment getPayment(@PathVariable("id") Integer id) {
	     Optional<Payment> optionalPayment = paymentRepository.findById(id);
	     if (optionalPayment.isPresent()) {
	         return optionalPayment.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji placanje sa id: " + id);
	     }
	 }

   /* @GetMapping("/paymentOrders")
	public Collection<Payment> getAddressByStreet(@RequestParam(required=true) String street) {
		return paymentRepository.findPaymentByOrdersIgnoreCase(orders);
	} */
/*    
    @PostMapping("/payment")
	public ResponseEntity<Payment> createPayment(@RequestBody Payment payment,
												 @RequestHeader(value="token", required = false) String token,
												 @RequestHeader(value="amount", required = false) Double amount,
												 @RequestHeader(value="userId", required = false) String userId) {
		if(paymentRepository.existsById(payment.getPaymentId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			if (token != null) {
				var charge = this.stripeClient.chargeNewCard(token, amount, userId);
			}
			paymentRepository.save(payment);
			return new ResponseEntity<Payment>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PutMapping("/payment/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable("id") int id, @RequestBody Payment newPayment) {		
    	Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji placanje sa id: " + id));
		payment.setPaymentMethod(newPayment.getPaymentMethod());
		Payment updatedPayment = paymentRepository.save(payment);
		
		return ResponseEntity.ok(updatedPayment);
	}
    
    @DeleteMapping("/payment/{id}")
   	public ResponseEntity<Payment> deletePayment(@PathVariable("id") int id){
   		if (!paymentRepository.existsById(id)) {
   			return new ResponseEntity<Payment>(HttpStatus.NO_CONTENT);
   		}
   		
   		paymentRepository.deleteById(id);		
   		return new ResponseEntity<Payment>(HttpStatus.OK);
   	}
}*/
/*
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment.getStripePaymentId(), payment.getAmount(), payment.getCurrency(), payment.getOrder());
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
    }
}


/*
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
    private PaymentRepository paymentRepository;

	@Autowired
	private StripeClient stripeClient;
	
	@GetMapping("/payment")
    public Collection<Payment> getAllPayment(){
		return paymentRepository.findAll();}
    

    @GetMapping("/payment/{id}")
    public Payment getPayment(@PathVariable("id") Integer id) {
	     Optional<Payment> optionalPayment = paymentRepository.findById(id);
	     if (optionalPayment.isPresent()) {
	         return optionalPayment.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji placanje sa id: " + id);
	     }
	 }
 
   /* @GetMapping("/paymentOrders")
	public Collection<Payment> getAddressByStreet(@RequestParam(required=true) String street) {
		return paymentRepository.findPaymentByOrdersIgnoreCase(orders);
	} */
    /*
    @PostMapping("/payment")
	public ResponseEntity<Payment> createPayment(@RequestBody Payment payment,
												 @RequestHeader(value="token", required = false) String token,
												 @RequestHeader(value="amount", required = false) Double amount,
												 @RequestHeader(value="userId", required = false) String userId) {
		if(paymentRepository.existsById(payment.getPaymentId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			if (token != null) {
				var charge = this.stripeClient.chargeNewCard(token, amount, userId);
			}
			paymentRepository.save(payment);
			return new ResponseEntity<Payment>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PutMapping("/payment/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable("id") int id, @RequestBody Payment newPayment) {		
    	Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji placanje sa id: " + id));
		payment.setPaymentMethod(newPayment.getPaymentMethod());
		Payment updatedPayment = paymentRepository.save(payment);
		
		return ResponseEntity.ok(updatedPayment);
	}
    
    @DeleteMapping("/payment/{id}")
   	public ResponseEntity<Payment> deletePayment(@PathVariable("id") int id){
   		if (!paymentRepository.existsById(id)) {
   			return new ResponseEntity<Payment>(HttpStatus.NO_CONTENT);
   		}
   		
   		paymentRepository.deleteById(id);		
   		return new ResponseEntity<Payment>(HttpStatus.OK);
   	}
}
*/