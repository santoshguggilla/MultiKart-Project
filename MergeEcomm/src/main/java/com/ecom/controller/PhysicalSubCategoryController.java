package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.beans.PhysicalCategory;
import com.ecom.beans.PhysicalSubCategory;
import com.ecom.service.PhysicalCategoryService;
import com.ecom.service.PhysicalSubCategoryService;
@Controller
@RequestMapping("/PhysicalSubCategory")
public class PhysicalSubCategoryController {
@Autowired	 
PhysicalCategoryService PhysicalCategoryServices;
@Autowired
PhysicalSubCategoryService PhysicalSubCategoryServices;
@RequestMapping("/addsubcatagory/{id}")
public String addSubCatagory(@PathVariable(value="id") int id,Model model,PhysicalSubCategory subcatagory) {
	System.out.println("test");

	PhysicalCategory catagory=PhysicalCategoryServices.getCategoryById(id);
	subcatagory.setCategoryId(catagory.getCategoryId());
	subcatagory.setCategoryName(catagory.getCategoryName());
	
	PhysicalSubCategory subcatagoryobject=PhysicalSubCategoryServices.addPhysicalSubCategory(subcatagory);
	model.addAttribute("catagory", catagory);
	model.addAttribute("subcatagoryobject", subcatagoryobject);
 	return "Physicalcategory-sub";
}
@RequestMapping("/savesubcatagory/{id}")

public String saveSubCatagory(PhysicalSubCategory subcatagory,@PathVariable(value="id") int id,Model model) {
	PhysicalCategory catagory=PhysicalCategoryServices.getCategoryById(id);
	subcatagory.setCategoryId(catagory.getCategoryId());
	subcatagory.setCategoryName(catagory.getCategoryName());
	PhysicalSubCategoryServices.addPhysicalSubCategory(subcatagory);
	
	
	return "redirect:/PhysicalSubCategory/subcatagory-list";
}
@GetMapping(value="/subcatagory-list")

//@RequestMapping("/subcatagory-list")
public String subCatagoryList(Model model) {
	List<PhysicalSubCategory> subcatagorylist=PhysicalSubCategoryServices.list();
	model.addAttribute("subcatagorylist", subcatagorylist);
	return "Physicalcatagory-sub-list";
}
 
 
 
 
 

@GetMapping(value="/getSubCatagory" , produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody List<PhysicalSubCategory> getSubCatagory(@RequestParam Integer catagoryId)
{

List<PhysicalSubCategory> list = PhysicalSubCategoryServices.findPhysicalSubCategoryByCategory(catagoryId);

System.out.println("catagoryId   "+catagoryId);

return list;
}


@GetMapping("/deletesubCategory/{id}")
public String deleteCategory(Model model, @PathVariable("id") int id) {

	System.out.println("ssss");
	PhysicalSubCategoryServices.deletePhysicalSubCategoryById(id);
	
	return "redirect:/PhysicalSubCategory/subcatagory-list";

}
@GetMapping("/editsubcategory/{id}")
public String EditBycategoryid(Model model, @PathVariable("id") int id) {
	PhysicalSubCategory obj = PhysicalSubCategoryServices.getPhysicalSubCategoryById(id);
 	model.addAttribute("categorieslist", obj);
 	return "EditPhysicalSubCategory";
}
@RequestMapping("/santhosh")
public String santhosh() {
	return"front-end-index";
}

}
