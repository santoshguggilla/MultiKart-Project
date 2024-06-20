package com.ecom.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecom.beans.Admin;
import com.ecom.beans.BilingDetails;
import com.ecom.service.BillingService;

@Controller
@RequestMapping("/Bill")
public class BillingController {

	@Autowired
	BillingService billingService;
	
	 
	@RequestMapping(value="/indexBill")
	public String getBackEndIndex(Model model,@ModelAttribute(value="objBilling") BilingDetails bilingDetails,HttpSession session) {
		model.addAttribute("objBilling", bilingDetails);
	 
		return "checkout";
		
	}
	 
	
	@RequestMapping(value="/AddBill")
	public String addBill(Model model,BilingDetails bilingDetails,HttpSession session) {
		
		
		 billingService.addBilingName(bilingDetails);
		  System.out.println("Add Billing"); 
			 
		
		return "redirect:/Bill/indexBill";
	}
	
}
