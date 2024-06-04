package com.clothesShop.mypcg.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Payment")
public class Payment {

	@Id
	@SequenceGenerator(name="PAYMENT_ID_GENERATOR", sequenceName="PAYMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAYMENT_ID_GENERATOR")
	private int paymentId;
	
	private String paymentMethod;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="orderId", referencedColumnName="orderId")
	private Order order;
	
	public Payment() {
		
	}

	public Payment(int payment_id, String payment_method, Order order) {
		super();
		this.paymentId = payment_id;
		this.paymentMethod = payment_method;
		this.order = order;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
/*@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stripe_payment_id")
    private String stripePaymentId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderid")
    private Order order;

    // Constructors
    public Payment() {}

    public Payment(String stripePaymentId, Double amount, String currency, Order order) {
        this.stripePaymentId = stripePaymentId;
        this.amount = amount;
        this.currency = currency;
        this.order = order;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStripePaymentId() {
        return stripePaymentId;
    }

    public void setStripePaymentId(String stripePaymentId) {
        this.stripePaymentId = stripePaymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
*/
