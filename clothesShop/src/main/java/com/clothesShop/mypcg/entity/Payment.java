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
