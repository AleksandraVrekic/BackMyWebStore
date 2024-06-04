package com.clothesShop.mypcg.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_item")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitemid")
    private Long orderItemId;
    
    @Column(name = "quantity")
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(int quantity, Product product, Order order) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    // Getters and setters
    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

