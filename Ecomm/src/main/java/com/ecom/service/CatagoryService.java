package com.ecom.service;

import java.util.List;


import com.ecom.beans.Catagory;
import com.ecom.beans.SubCatagory;

public interface CatagoryService {
	Catagory addCatagory(Catagory name);
	List<Catagory> getAllCatagory();
	List<Catagory> getAllCatagoryById(int id);
	List<Catagory> getAllCatagoryById(Iterable<Integer> id);
	Catagory getCatagoryById(int id);
	void deleteCategoryById(int id);
	
	public Iterable<Catagory> findAll();

	public Catagory find(int id);
	//List<SubCatagory> findSubCatagoryByCatagory(Integer catagoryId);
}
