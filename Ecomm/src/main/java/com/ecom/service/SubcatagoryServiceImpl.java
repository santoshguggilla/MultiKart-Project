package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.SubCatagory;
import com.ecom.repository.SubcatagoryRepository;
@Service
public class SubcatagoryServiceImpl implements SubcatagoryService{
@Autowired
SubcatagoryRepository subcatagoryrepository;
	@Override
	public SubCatagory addSubcatagory(SubCatagory details) {
		// TODO Auto-generated method stub
		return subcatagoryrepository.save(details);
	}

	@Override
	public SubCatagory getDataById(int id) {
		// TODO Auto-generated method stub
		return subcatagoryrepository.getById(id);
	}

	@Override
	public List<SubCatagory> list() {
		// TODO Auto-generated method stub
		return subcatagoryrepository.findAll();
	}

	@Override
	public List<SubCatagory> findByCatagory(int id) {
		// TODO Auto-generated method stub
		return subcatagoryrepository.findAll();
	}

	@Override
	public List<SubCatagory> findSubCatagoryByCatagory(int id) {
		// TODO Auto-generated method stub
		return subcatagoryrepository.findSubCategoryByCategory(id);
	}

	@Override
	public SubCatagory getSubCatagoryById(int id) {
		// TODO Auto-generated method stub
		return subcatagoryrepository.getById(id);
	}

	@Override
	public void deleteSubCategoryById(int id) {
		// TODO Auto-generated method stub
		subcatagoryrepository.deleteById(id);
	}

}
