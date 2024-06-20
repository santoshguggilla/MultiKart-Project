package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.DigitalProducts;
import com.ecom.repository.DigitalProductsRepository;

@Service
public class DigitalProductsServiceImpl implements DigitalProductsService {
	@Autowired
	DigitalProductsRepository repository;

	@Override
	public DigitalProducts saveDigitalProductData(DigitalProducts data) {
		// TODO Auto-generated method stub
		return repository.save(data);
	}

	@Override
	public void deleteBydataId(int dataid) {
		// TODO Auto-generated method stub
		repository.deleteById(dataid);
	}

	public DigitalProducts getDetails(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id).get();
	}

	@Override
	public List<DigitalProducts> getAllDigitalProductsData() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<DigitalProducts> getDigitalByName(String keyword) {
		// TODO Auto-generated method stub
		return repository.findByKeyword(keyword);
	}

	@Override
	public List<DigitalProducts> getAllDigitalProductsData(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DigitalProducts addProduct(DigitalProducts productobject) {
		// TODO Auto-generated method stub
		return repository.save(productobject);
	}

	@Override
	public DigitalProducts getProductById(int productid) {
		// TODO Auto-generated method stub
		return repository.findById(productid).get();
	}

	@Override
	public List<DigitalProducts> getAllProduct() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
