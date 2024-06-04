package com.clothesShop.mypcg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.entity.Payment;
import com.clothesShop.mypcg.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
/*
@RestController
public class StripeWebhookController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        final String endpointSecret = "whsec_59a6b865427305f06884249955b0c73078f3792b0058121ff8c1ed16f921d189";  // Tajni ključ koji dobijate od Stripe-a
        try {
            Event event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
            );

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                    handleSuccessfulPayment(paymentIntent);
                    break;
                case "payment_intent.payment_failed":
                    System.out.println("Payment Failed.");
                    break;
            }
            return ResponseEntity.ok("Event received");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook Error: " + e.getMessage());
        }
    }

    private void handleSuccessfulPayment(PaymentIntent paymentIntent) {
        // Retrieve order ID from paymentIntent metadata
        Long orderId = Long.parseLong(paymentIntent.getMetadata().get("order_id"));
        
        // Attempt to find the order
        Optional<Order> optionalOrder = orderService.findOrderById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Update order status
            orderService.updateOrderStatus(orderId, "PAID");

            // Record payment details
            Payment payment = paymentService.recordPaymentDetails(paymentIntent, order);
        } else {
            // Log or handle the case where no order was found
            System.out.println("Order not found for ID: " + orderId);
        }
    }

}
*/
