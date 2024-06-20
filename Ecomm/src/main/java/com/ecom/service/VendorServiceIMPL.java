package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ecom.beans.Vendor;
import com.ecom.repository.VendorRepository;
/*
 * Author: Santhosh
 */
@Service
public class VendorServiceIMPL implements VendorService{
	
	@Autowired
	VendorRepository vendorRepository;

	@Override
	public Vendor addVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		return vendorRepository.save(vendor);
	}

	@Override
	public List<Vendor> getAllVendors() {
		// TODO Auto-generated method stub
		return vendorRepository.findAll();
	}

	@Override
	public void deleteVendorById(int id) {
		// TODO Auto-generated method stub
		
		
		try {
			vendorRepository.deleteById(id);
		} catch (DataAccessException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
	}

	@Override
	public Vendor getVendorById(int id) {
		// TODO Auto-generated method stub
		return vendorRepository.findById(id).get();
	}

}
