package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.beans.SubCatagory;

@Repository
public interface SubcatagoryRepository extends JpaRepository<SubCatagory, Integer>{
 
	
	
	
	@Query(value = "SELECT al FROM SubCatagory al WHERE al.catagoryId =?1")
	List<SubCatagory> findSubCategoryByCategory(Integer id);
 
}
