package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.BilingDetails;
import com.ecom.repository.BillingRepository;

@Service
public class BillingServiceIMPL implements BillingService{

	@Autowired
	BillingRepository bilinBillingRepository;
	@Override
	public BilingDetails addBilingName(BilingDetails bilingDetails) {
		// TODO Auto-generated method stub
		return bilinBillingRepository.save(bilingDetails);
	}

}
