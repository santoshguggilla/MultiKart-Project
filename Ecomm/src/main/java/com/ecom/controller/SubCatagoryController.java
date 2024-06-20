package com.ecom.controller;

import java.util.List;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ecom.beans.Catagory;
import com.ecom.beans.SubCatagory;
import com.ecom.beans.Vendor;
import com.ecom.service.CatagoryService;
import com.ecom.service.SubcatagoryService;
 @Controller
@RequestMapping("/subcatagory")
public class SubCatagoryController {
	@Autowired
	CatagoryService catagoryservice;
@Autowired
SubcatagoryService subcatagoryservice;
@RequestMapping("/add-subcatagory/{id}")
public String addSubCatagory(@PathVariable(value="id") int id,Model model,SubCatagory subcatagory) {
	Catagory catagory=catagoryservice.getCatagoryById(id);
	subcatagory.setCatagoryId(catagory.getCatagoryId());
	subcatagory.setCatagoryName(catagory.getCatagoryName());
	
	SubCatagory subcatagoryobject=subcatagoryservice.addSubcatagory(subcatagory);
	model.addAttribute("catagory", catagory);
	model.addAttribute("subcatagoryobject", subcatagoryobject);
	
	return "category-sub";
}
@RequestMapping("/savesubcatagory/{id}")

public String saveSubCatagory(SubCatagory subcatagory,@PathVariable(value="id") int id,Model model) {
	Catagory catagory=catagoryservice.getCatagoryById(id);
	subcatagory.setCatagoryId(catagory.getCatagoryId());
	subcatagory.setCatagoryName(catagory.getCatagoryName());
	subcatagoryservice.addSubcatagory(subcatagory);
	
	
	return "redirect:/subcatagory/subcatagory-list";
}
@GetMapping(value="/subcatagory-list")

//@RequestMapping("/subcatagory-list")
public String subCatagoryList(Model model) {
	List<SubCatagory> subcatagorylist=subcatagoryservice.list();
	model.addAttribute("subcatagorylist", subcatagorylist);
	return "catagory-sub-list";
}
 
@RequestMapping(method = RequestMethod.GET)
public String index(ModelMap modelMap) {
	modelMap.put("countries", catagoryservice.findAll());
	return "add-digital-product";
}
  

@GetMapping(value="/getSubCatagory" , produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody List<SubCatagory> getSubCatagory(@RequestParam Integer catagoryId)
{

List<SubCatagory> list = subcatagoryservice.findSubCatagoryByCatagory(catagoryId);

System.out.println("catagoryId   "+catagoryId);

return list;
}

@GetMapping("/deleteSubCategory/{id}")
public String deleteSubCategory(Model model, @PathVariable("id") int id) {

	System.out.println("ssss");
	subcatagoryservice.deleteSubCategoryById(id);
	
	return "redirect:/catagory/catagorylist";

}
@GetMapping("/editSubcategory/{id}")
public String EditBycategoryid(Model model, @PathVariable("id") int id) {
	SubCatagory obj = subcatagoryservice.getSubCatagoryById(id);
	//List<Category> catagory=service.getAllCategoryDetails();
	model.addAttribute("categorieslist", obj);
	//model.addAttribute("catagory", catagory);
	return "EditSubCategory";
}
}
