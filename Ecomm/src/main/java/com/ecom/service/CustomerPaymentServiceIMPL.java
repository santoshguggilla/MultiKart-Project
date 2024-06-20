package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.CustomerPaymentDetails;
import com.ecom.repository.CustomerPaymentRepository;

@Service
public class CustomerPaymentServiceIMPL implements CustomerPaymentService{
	
	@Autowired
	CustomerPaymentRepository customerPaymentRepository;

	@Override
	public CustomerPaymentDetails addCustomerPaymentDetails(CustomerPaymentDetails details) {
		// TODO Auto-generated method stub
		return customerPaymentRepository.save(details);
	}

	@Override
	public void updateCustomerPaymentDetails(CustomerPaymentDetails details) {
		// TODO Auto-generated method stub
		customerPaymentRepository.save(details);
	}

	@Override
	public List<CustomerPaymentDetails> getAllCustomerPaymentDetails() {
		// TODO Auto-generated method stub
		return customerPaymentRepository.findAll();
	}

	@Override
	public void deleteCustomerPaymentDetailsById(int id) {
		// TODO Auto-generated method stub
		customerPaymentRepository.deleteById(id);
	}

	@Override
	public CustomerPaymentDetails getCustomerPaymentDetailsById(int id) {
		// TODO Auto-generated method stub
		return customerPaymentRepository.getById(id);
	}

}
