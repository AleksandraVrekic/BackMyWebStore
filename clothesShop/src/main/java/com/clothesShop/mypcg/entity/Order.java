package com.clothesShop.mypcg.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double amount;

    @Column(name = "customer_address", length = 255, nullable = false)
    private String customerAddress;

    @Column(name = "customer_email", length = 128, nullable = false)
    private String customerEmail;

    @Column(name = "customer_name", length = 255, nullable = false)
    private String customerName;

    @Column(name = "customer_phone", length = 128, nullable = false)
    private String customerPhone;

    @Column(name = "order_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @Column(name = "order_num", nullable = false, unique = true)
    private Integer orderNum;

    // Constructors, getters, and setters

    public Order() {
    }

    public Order(double amount, String customerAddress, String customerEmail, String customerName, String customerPhone, LocalDate orderDate, Integer orderNum) {
        this.amount = amount;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.orderDate = orderDate;
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}

