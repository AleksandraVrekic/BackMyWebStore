package com.clothesShop.mypcg.controller;

import java.util.Optional;
import com.clothesShop.mypcg.dto.Transaction;
import com.clothesShop.mypcg.dto.TransactionItem;
import com.clothesShop.mypcg.entity.Account;
import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.entity.OrderItem;
import com.clothesShop.mypcg.entity.Product;

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
import com.clothesShop.mypcg.repository.AccountRepository;
import com.clothesShop.mypcg.repository.OrderRepository;
import com.clothesShop.mypcg.repository.ProductRepository;
import com.clothesShop.mypcg.repository.TransactionRepository;
import com.clothesShop.mypcg.service.EmailService;
import com.google.gson.Gson;

import java.util.ArrayList;

@RestController
@RequestMapping("webhook")
public class WebhookController {

    private static final Gson gson = new Gson();

    @Value("${stripe.webhook_key}")
    private String stripeKey;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private EmailService emailService;

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

         // Pretpostavljamo da email adresa klijenta dolazi kao parametar
            Account account = accountRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + customerEmail));
            
            if (account != null) {
                // Kreiramo narudžbinu (Order)
                Order order = new Order();
                order.setAccount(account);  // Postavljamo account sa emailom iz Account objekta
                order.setTotalPrice(amount / 100.0); // Čuvamo iznos u pravom formatu
                order.setOrderStatus("succeeded");

                List<OrderItem> items = new ArrayList<>();

                for (String key : paymentIntent.getMetadata().keySet()) {
                    if (key.startsWith("product_")) {
                        String productId = key.split("_")[1];
                        int quantity = Integer.parseInt(paymentIntent.getMetadata().get(key));

                     // Promeni iz Long na Integer
                        Product product = productRepository.findById(Integer.parseInt(productId))
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                        // Kreiranje novog OrderItem objekta
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProduct(product);  // Postavljamo ceo Product objekat
                        orderItem.setQuantity(quantity);
                        orderItem.setOrder(order);  // Povezujemo sa narudžbinom

                        items.add(orderItem);
                    }
                }

                order.setOrderItems(items);
                orderRepository.save(order); // Čuvamo narudžbinu

                // Slanje potvrde putem emaila
                String emailBody = generateEmailBody(order);
                emailService.sendSimpleEmail(account.getEmail(), "Order Confirmation", emailBody);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer account not found");
            }
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
    private String generateEmailBody(Order order) {
        StringBuilder body = new StringBuilder();
        body.append("Thank you for your purchase!\n\n");
        body.append("Order Details:\n");
        body.append("Order ID: ").append(order.getOrderId()).append("\n");
        body.append("Total Price: ").append(order.getTotalPrice()).append(" USD\n");
        body.append("Items:\n");
        
        for (OrderItem item : order.getOrderItems()) {
            body.append(" - Product Name: ").append(item.getProduct().getName()) // Preuzmi ime proizvoda
                .append(", Quantity: ").append(item.getQuantity())
                .append("\n");
        }
        
        return body.toString();
    }
}