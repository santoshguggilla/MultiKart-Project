package com.ecom.service;

import java.util.List;

import com.ecom.beans.CustomerPaymentDetails;


public interface CustomerPaymentService {
	
	CustomerPaymentDetails addCustomerPaymentDetails(CustomerPaymentDetails details);
	
	void updateCustomerPaymentDetails(CustomerPaymentDetails details);
	
	List<CustomerPaymentDetails> getAllCustomerPaymentDetails();
	
    void deleteCustomerPaymentDetailsById(int id);
	
    CustomerPaymentDetails getCustomerPaymentDetailsById(int id);

}
