package com.ecom.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.beans.Catagory;
import com.ecom.beans.SubCatagory;
import com.ecom.service.CatagoryService;
import com.ecom.service.SubcatagoryService;

@Controller
@RequestMapping("/catagory")
public class CatagoryController {

	@Autowired
	CatagoryService catagoryservice;
	@Autowired
	SubcatagoryService subcatagoryservice;
	
	@RequestMapping("/catagorylist")
	public String addCatagory(Model model,Catagory catagory) {
		List<Catagory> catagorylist=catagoryservice.getAllCatagory();
		model.addAttribute("catagorylist", catagorylist);
		model.addAttribute("catagory", catagory);
		return "category";
	}
	@RequestMapping("/savecatagory")
	public String saveCatagory(Catagory catagory) {
		catagoryservice.addCatagory(catagory);
		return"redirect:/catagory/catagorylist";
	}
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(Model model, @PathVariable("id") int id) {

		System.out.println("ssss");
		catagoryservice.deleteCategoryById(id);
		
		return "redirect:/catagory/catagorylist";

	}
	@GetMapping("/editcategory/{id}")
	public String EditBycategoryid(Model model, @PathVariable("id") int id) {
		Catagory obj = catagoryservice.getCatagoryById(id);
		//List<Category> catagory=service.getAllCategoryDetails();
		model.addAttribute("categorieslist", obj);
		//model.addAttribute("catagory", catagory);
		return "EditCategory";
	}

}
