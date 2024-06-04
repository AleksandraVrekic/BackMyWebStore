package com.clothesShop.mypcg.service;

import com.clothesShop.mypcg.dto.PaymentInfo;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

	PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
