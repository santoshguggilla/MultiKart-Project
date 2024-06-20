package com.ecom.service;

import java.util.List;

import com.ecom.beans.Customer;



public interface CustomerRegistrationService {
	Customer addCustomer(Customer customer);
	List<Customer> getAllCustomerRegistration();
	void deleteBydataId(int customerId);
	Customer getCustomer(String username,String password);
	void updateBydataId(Customer customer);
	Customer getCustomerById(int customerid);
}
