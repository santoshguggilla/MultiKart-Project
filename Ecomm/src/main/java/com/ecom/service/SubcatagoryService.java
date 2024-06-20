package com.ecom.service;


import java.util.List;

import com.ecom.beans.Catagory;
import com.ecom.beans.SubCatagory;

public interface SubcatagoryService {

	 SubCatagory addSubcatagory(SubCatagory details);
	 SubCatagory getDataById(int id);

	 List<SubCatagory> list();
	 SubCatagory getSubCatagoryById(int id);
		void deleteSubCategoryById(int id);
	 
		public List<SubCatagory> findByCatagory(int id);
		List<SubCatagory> findSubCatagoryByCatagory(int id);
 
}

