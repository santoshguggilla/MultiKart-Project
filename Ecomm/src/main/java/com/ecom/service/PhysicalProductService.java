package com.ecom.service;

import java.util.List;

import com.ecom.beans.PhysicalProducts;


public interface PhysicalProductService {
	PhysicalProducts addProduct(PhysicalProducts productobject);
	PhysicalProducts getProductById(int productid);
	
	List<PhysicalProducts> getAllProduct();
	void deleteBydataId(int id);
	List<PhysicalProducts> getLatestProducts();
	
}
