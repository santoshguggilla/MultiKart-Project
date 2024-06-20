package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.beans.Catagory;
import com.ecom.beans.PhysicalCategory;
import com.ecom.service.PhysicalCategoryServices;
import com.ecom.service.SubcatagoryService;
@Controller
@RequestMapping("/PhysicalCatagory")
public class PhysicalCatagoryController {

	@Autowired
	PhysicalCategoryServices PhysicalCategoryServices;
	@Autowired
	SubcatagoryService subcatagoryservice;
	
	@RequestMapping("/catagorylist")
	public String addCatagory(Model model,Catagory catagory) {
		List<PhysicalCategory> catagorylist=PhysicalCategoryServices.getAllCatagory();
		model.addAttribute("catagorylist", catagorylist);
		model.addAttribute("catagory", catagory);
		return "Physicalcategory";
	}
	@RequestMapping("/savecatagory")
	public String saveCatagory(PhysicalCategory catagory) {
		PhysicalCategoryServices.addCatagory(catagory);
		return"redirect:/PhysicalCatagory/catagorylist";
	}
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(Model model, @PathVariable("id") int id) {

		System.out.println("ssss");
		PhysicalCategoryServices.deleteCategoryById(id);
		
		return "redirect:/PhysicalCatagory/catagorylist";

	}
	@GetMapping("/editcategory/{id}")
	public String EditBycategoryid(Model model, @PathVariable("id") int id) {
		PhysicalCategory obj = PhysicalCategoryServices.getCatagoryById(id);
		//List<Category> catagory=service.getAllCategoryDetails();
		model.addAttribute("categorieslist", obj);
		//model.addAttribute("catagory", catagory);
		return "EditPhysicalCategory";
	}

}
