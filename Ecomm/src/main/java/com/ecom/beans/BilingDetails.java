package com.ecom.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Billing")
public class BilingDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billingId;
	private String billingFirstName;
	private String billingLastName;
	private long bilingPhone;
	private String billingEmail;
	private String billingContry;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private int billingPin;
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public String getBillingFistName() {
		return billingFirstName;
	}
	public void setBillingFistName(String billingFistName) {
		this.billingFirstName = billingFistName;
	}
	public String getBillingLastName() {
		return billingLastName;
	}
	public void setBillingLastName(String billingLastName) {
		this.billingLastName = billingLastName;
	}
	public long getBilingPhone() {
		return bilingPhone;
	}
	public void setBilingPhone(long bilingPhone) {
		this.bilingPhone = bilingPhone;
	}
	public String getBillingEmail() {
		return billingEmail;
	}
	public void setBillingEmail(String billingEmail) {
		this.billingEmail = billingEmail;
	}
	public String getBillingContry() {
		return billingContry;
	}
	public void setBillingContry(String billingContry) {
		this.billingContry = billingContry;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public int getBillingPin() {
		return billingPin;
	}
	public void setBillingPin(int billingPin) {
		this.billingPin = billingPin;
	}
	
	
	

}
