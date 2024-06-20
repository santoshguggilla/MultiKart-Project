package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

 import com.ecom.beans.PhysicalCategory;
import com.ecom.repository.PhysicalCategoryRepository;
@Repository
public class PhysicalCategoryServiceImpl implements PhysicalCategoryServices{
@Autowired
PhysicalCategoryRepository  PhysicalCategoryRepository ;

@Override
public PhysicalCategory addCatagory(PhysicalCategory name) {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.save(name);
}

@Override
public List<PhysicalCategory> getAllCatagory() {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.findAll();
}

@Override
public List<PhysicalCategory> getAllCatagoryById(int id) {
	// TODO Auto-generated method stub
	return  null;
}

@Override
public List<PhysicalCategory> getAllCatagoryById(Iterable<Integer> id) {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.findAllById(id);
}

@Override
public PhysicalCategory getCatagoryById(int id) {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.getById(id);
}

@Override
public void deleteCategoryById(int id) {
	// TODO Auto-generated method stub
	PhysicalCategoryRepository.deleteById(id);
}

@Override
public Iterable<PhysicalCategory> findAll() {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.findAll();
}

@Override
public PhysicalCategory find(int id) {
	// TODO Auto-generated method stub
	return PhysicalCategoryRepository.findById(id).get();
}

 

  

}
