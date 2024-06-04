package com.clothesShop.mypcg.dto;

import lombok.Data;

@Data
public class PaymentInfo {

    private int amount;
    private String currency;


    // Getter for amount
    public int getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Getter for currency
    public String getCurrency() {
        return currency;
    }

    // Setter for currency
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}