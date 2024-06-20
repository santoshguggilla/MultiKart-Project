package com.ecom.service;

import java.util.List;

import com.ecom.beans.PhysicalSubCategory;
import com.ecom.beans.SubCatagory;
 
public interface PhysicalSubCategoryServices {
	 PhysicalSubCategory addPhysicalSubCategory(PhysicalSubCategory subcatagory);
	 PhysicalSubCategory getDataById(int id);

	 List<PhysicalSubCategory> list();
	 PhysicalSubCategory getPhysicalSubCategoryById(int id);
		void deletePhysicalSubCategoryById(int id);
	 
		public List<PhysicalSubCategory> findByCatagory(int id);
		List<PhysicalSubCategory> findPhysicalSubCategoryByCatagory(int id);
 
}
