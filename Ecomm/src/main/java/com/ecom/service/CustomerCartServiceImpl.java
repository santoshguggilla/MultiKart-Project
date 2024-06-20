package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.CustomerCart;
import com.ecom.repository.CustomerCartERepository;
@Service
public class CustomerCartServiceImpl implements CustomerCartService {

	
	@Autowired
	CustomerCartERepository customerCartERepository;
	
	@Override
	public CustomerCart addCart(CustomerCart customerCart) {
		// TODO Auto-generated method stub
		return customerCartERepository.save(customerCart);
	}

	@Override
	public List<CustomerCart> getAllCart() {
		// TODO Auto-generated method stub
		return  customerCartERepository.findAll() ;
	}

	@Override
	public void deleteBydataId(int id) {
		customerCartERepository.deleteById(id);
		
	}

}
