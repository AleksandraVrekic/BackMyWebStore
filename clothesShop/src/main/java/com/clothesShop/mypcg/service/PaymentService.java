package com.clothesShop.mypcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clothesShop.mypcg.dto.CartItem;
import com.clothesShop.mypcg.dto.PaymentInfo;
import com.clothesShop.mypcg.entity.Order;
import com.clothesShop.mypcg.entity.Payment;
import com.clothesShop.mypcg.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.*;

@Service
public class PaymentService {

    public PaymentService(@Value("${stripe.key.secret}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        // Dodavanje metapodataka
        Map<String, String> metadata = new HashMap<>();
        metadata.put("customer_email", paymentInfo.getCustomerEmail());
        for (CartItem item : paymentInfo.getItems()) {
            metadata.put("product_" + item.getProductId() + "_quantity", String.valueOf(item.getQuantity()));
        }
        params.put("metadata", metadata);

        return PaymentIntent.create(params);
    }
}
