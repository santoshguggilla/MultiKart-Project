package com.ecom.service;

import java.util.List;

 import com.ecom.beans.PhysicalCategory;

public interface PhysicalCategoryServices {
	PhysicalCategory addCatagory(PhysicalCategory name);
	List<PhysicalCategory> getAllCatagory();
	List<PhysicalCategory> getAllCatagoryById(int id);
	List<PhysicalCategory> getAllCatagoryById(Iterable<Integer> id);
	PhysicalCategory getCatagoryById(int id);
	void deleteCategoryById(int id);
	
	public Iterable<PhysicalCategory> findAll();

	public PhysicalCategory find(int id);
 }
