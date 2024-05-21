package com.clothesShop.mypcg.entity;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position")
    private String position;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id") // Ovde je ključ u tabeli Customer koji referiše na Account
    private Account account;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Constructors

    public Staff() {
    }

    // Constructor with all fields
    public Staff(Integer id, String position, Account account) {
        this.id = id;
        this.position = position;
        this.account = account;
    }
}
