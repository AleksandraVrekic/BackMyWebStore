package com.clothesShop.mypcg.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id") // Ključ u tabeli Customer koji referiše na Account
    private Account account;

    // Getteri i setteri

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Konstruktori

    public Customer() {
    }

    // Konstruktor sa svim poljima
    public Customer(Integer id, String phone, Address address, Account account) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.account = account;
    }
}
