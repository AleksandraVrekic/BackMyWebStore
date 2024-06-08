package com.clothesShop.mypcg.entity;




import javax.persistence.*;

import com.clothesShop.mypcg.dto.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Long orderId;

    @Column(name = "orderdate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "orderstatus")
    private String orderStatus;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "discount")
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
   
    
	@OneToMany(mappedBy="order", cascade = {CascadeType.ALL})
	private List<OrderItem> orderItems = new ArrayList<>();
	/*
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
    */
    // Method to format the date
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
        return sdf.format(orderDate);
    }

    // Constructors
    public Order() {
    }

    public Order(Date orderDate, String orderStatus, Double totalPrice, Double discount, Account account) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.account = account;
    }

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		for (var item : orderItems) {
			item.setOrder(this);
		}
		 this.orderItems = orderItems;
	}
	
}
