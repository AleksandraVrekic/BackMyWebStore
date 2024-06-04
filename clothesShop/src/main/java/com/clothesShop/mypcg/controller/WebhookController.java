package com.clothesShop.mypcg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.net.Webhook;
import com.stripe.model.StripeObject;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.google.gson.Gson;

@RestController
@RequestMapping("webhook")
public class WebhookController {

    private static final Gson gson = new Gson();

    @Value("${stripe.webhook_key}")
    private String stripeKey;


    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            // Verify signature only if endpoint secret is defined
            if (stripeKey != null && sigHeader != null) {
                event = Webhook.constructEvent(payload, sigHeader, stripeKey);
            } else {
                // Deserialize event using GSON without signature verification
                event = gson.fromJson(payload, Event.class);
            }
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
            case "charge.succeeded":
                // Handle successful payment
                break;
            case "payment_method.attached":
                // Handle attached payment method
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("Success");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
