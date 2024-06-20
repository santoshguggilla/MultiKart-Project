package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.Catagory;
import com.ecom.beans.SubCatagory;
import com.ecom.repository.CatagoryRepository;
@Service
public class CatagoryServiceImpl implements CatagoryService {
@Autowired
CatagoryRepository catagoryrepository;

@Override
public Catagory addCatagory(Catagory name) {
	// TODO Auto-generated method stub
	return catagoryrepository.save(name);
}

@Override
public List<Catagory> getAllCatagory() {
	// TODO Auto-generated method stub
	return catagoryrepository.findAll();
}

@Override
public List<Catagory> getAllCatagoryById(Iterable<Integer> id) {
	// TODO Auto-generated method stub
	return catagoryrepository.findAllById(id);
}

@Override
public List<Catagory> getAllCatagoryById(int id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Catagory getCatagoryById(int id) {
	// TODO Auto-generated method stub
	return catagoryrepository.getById(id);
}

@Override
public void deleteCategoryById(int id) {
	// TODO Auto-generated method stub
	catagoryrepository.deleteById(id);
}

@Override
public Iterable<Catagory> findAll() {
	// TODO Auto-generated method stub
	return catagoryrepository.findAll();
}

@Override
public Catagory find(int id) {
	// TODO Auto-generated method stub
	return catagoryrepository.findById(id).get();
}

 

}
