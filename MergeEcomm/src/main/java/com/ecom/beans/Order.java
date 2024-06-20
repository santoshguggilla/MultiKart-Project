package com.ecom.beans;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table; 

@Entity
@Table(name="orderCustomer")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String TransactionId;
	private long orderId;
	private LocalDate orderDate;
	private LocalTime localTime;
	private long carrierNo;
	private int orderNo;
	
	 
	
	
 
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int random_int3) {
		this.orderNo = random_int3;
	}
	public long getCarrierNo() {
		return carrierNo;
	}
	public void setCarrierNo(long carrierNo) {
		this.carrierNo = carrierNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public LocalTime getLocalTime() {
		return localTime;
	}
	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	
}
