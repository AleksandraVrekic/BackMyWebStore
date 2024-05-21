package com.clothesShop.mypcg.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="address")
public class Address {

    @Id
    @SequenceGenerator(name="ADDRESS_ID_GENERATOR", sequenceName="address_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADDRESS_ID_GENERATOR")
    @Column(name = "addressid")  // ispravno ime kolone
    private int addressId;

    @Column(name="street")
    private String street;

    @Column(name="country")
    private String country;

    @Column(name="city")
    private String city;

    @Column(name="zip")
    private int zip;

    @OneToMany(mappedBy="address", cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JsonIgnore
    private List<Customer> customers;

    // Getteri i setteri

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    // Konstruktori

    public Address() {
    }

    public Address(String street, String country, String city, int zip) {
        this.street = street;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }

    public Address(int addressId, String street, String country, String city, int zip) {
        this.addressId = addressId;
        this.street = street;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }
}

