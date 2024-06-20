package com.ecom.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecom.beans.Admin;
import com.ecom.beans.Vendor;
import com.ecom.service.AdminService;
import com.ecom.service.VendorService;

@Controller
@RequestMapping("/vendor")
public class VendorController {
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value="/create-vendor")
	public String getVendors(Model model,@ModelAttribute(value="vendor") Vendor vendor,HttpSession session) {
		
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		}
		else {
		Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
		model.addAttribute("admin", admin);
		}
		
		model.addAttribute("vendor", vendor);
		return "vendor-create";
		
	}
	
	@RequestMapping(value="/saveVendor",method=RequestMethod.POST)
	public String saveVendor(Model model,@ModelAttribute(value="vendorObj") Vendor vendor,HttpSession session) {
		
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			vendor.setCreatedBy(0);
			vendor.setUpdatedBy(0);
		}
		else {
			vendor.setCreatedBy(sessionAdmin.getEmployeeId());
			vendor.setUpdatedBy(sessionAdmin.getEmployeeId());
		}

		vendor.setIsActive('Y');
		vendor.setCreated(LocalDate.now());
		vendor.setUpdated(LocalDate.now());
		vendorService.addVendor(vendor);
		return "redirect:/vendor/vendor-list";
	}

	
	@GetMapping(value="/vendor-list")
	public String getAllVendors(Model model,HttpSession session) {
		List<Vendor> list = vendorService.getAllVendors();
		
		List<Vendor> vendorlist=new ArrayList<>();
		for(Vendor vendor:list) {
		if(vendor.getIsActive()=='Y'||vendor.getIsActive()=='y') {
			vendorlist.add(vendor);
		}
		}
		model.addAttribute("vendorlist", vendorlist);
		
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		}
		else {
		Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
		model.addAttribute("admin", admin);
		}
		
		
		return "vendor-list";
	}
	
	@GetMapping("/delete-vendor/{id}")
	public String deleteVendor(Model model,@PathVariable("id") int id) {
		
		Vendor vendor = vendorService.getVendorById(id);
		vendor.setIsActive('N');
		vendorService.addVendor(vendor);
		return "redirect:/vendor/vendor-list";
		
	}
	
	@GetMapping("/edit-vendor/{id}")
	public String getVendorById(Model model,@PathVariable("id") int id) {
		
		Vendor vendorObj = vendorService.getVendorById(id);
		model.addAttribute("vendorObj", vendorObj);
		return "edit-vendor";
	}
	

}
