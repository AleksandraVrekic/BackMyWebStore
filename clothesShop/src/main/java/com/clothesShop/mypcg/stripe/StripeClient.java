package com.clothesShop.mypcg.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

/*
@Component
public class StripeClient {

    @Value("${stripe.api.key}")
    private String stripeKey;

    public StripeClient() {
        Stripe.apiKey = stripeKey;
    }
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }
    public Charge chargeNewCard(String token, double amount, String userId) throws Exception {
        Stripe.apiKey = stripeKey;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "RSD");
        chargeParams.put("source", token);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", userId);
        chargeParams.put("metadata", metadata);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "RSD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}*/
/*
@Component
public class StripeClient {

    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    public Customer createStripeCustomer(String email, String token) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
            .setEmail(email)
            .setPaymentMethod(token)
            .build();

        return Customer.create(params);
    }

    public Charge chargeCreditCard(String token, double amount) throws StripeException {
        // Kreiranje parametara za naplatu koristeći Builder Pattern
        ChargeCreateParams params = ChargeCreateParams.builder()
            .setAmount((long) (amount * 100)) // Konverzija iznosa u cente
            .setCurrency("rsd") // Postavljanje valute na RSD
            .setSource(token) // Token koji predstavlja način plaćanja
            .build();

        // Kreiranje i vraćanje naplate
        return Charge.create(params);
    }

    public Customer retrieveCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }
}*/
