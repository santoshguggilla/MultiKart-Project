package com.ecom.service;

import java.util.List;


import com.ecom.beans.DigitalProducts;

public interface DigitalProductsService {

	DigitalProducts saveDigitalProductData(DigitalProducts data);
	List<DigitalProducts> getAllDigitalProductsData();
	void deleteBydataId(int dataid);
	DigitalProducts getDetails(int id);
	List<DigitalProducts> getAllDigitalProductsData(String keyword);
	List<DigitalProducts> getDigitalByName(String keyword);
	DigitalProducts addProduct(DigitalProducts productobject);
	DigitalProducts getProductById(int productid);
	List<DigitalProducts> getAllProduct();
	 
	
 }
