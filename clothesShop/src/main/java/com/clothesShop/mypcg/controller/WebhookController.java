package com.clothesShop.mypcg.controller;

import java.util.Optional;
import com.clothesShop.mypcg.dto.Transaction;
import com.clothesShop.mypcg.dto.TransactionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.net.Webhook;
import com.stripe.model.StripeObject;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.clothesShop.mypcg.repository.TransactionRepository;
import com.google.gson.Gson;
import java.util.ArrayList;

@RestController
@RequestMapping("webhook")
public class WebhookController {

    private static final Gson gson = new Gson();

    @Value("${stripe.webhook_key}")
    private String stripeKey;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            if (stripeKey != null && sigHeader != null) {
                event = Webhook.constructEvent(payload, sigHeader, stripeKey);
            } else {
                event = gson.fromJson(payload, Event.class);
            }
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed signature verification");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
            String customerEmail = paymentIntent.getMetadata().get("customer_email");
            long amount = paymentIntent.getAmountReceived();
            String currency = paymentIntent.getCurrency();

            Transaction transaction = new Transaction();
            transaction.setCustomerEmail(customerEmail);
            transaction.setCurrency(currency);
            transaction.setAmount(amount);

            List<TransactionItem> items = new ArrayList<>();

            for (String key : paymentIntent.getMetadata().keySet()) {
                if (key.startsWith("product_")) {
                    String productId = key.split("_")[1];
                    int quantity = Integer.parseInt(paymentIntent.getMetadata().get(key));
                    TransactionItem transactionItem = new TransactionItem();
                    transactionItem.setProductId(productId);
                    transactionItem.setQuantity(quantity);
                    transactionItem.setTransaction(transaction);

                    items.add(transactionItem);
                }
            }

            transaction.setItems(items);
            transactionRepository.save(transaction);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}