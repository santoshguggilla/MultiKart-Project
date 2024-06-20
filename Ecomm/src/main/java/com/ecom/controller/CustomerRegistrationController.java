package com.ecom.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecom.beans.CustomerAddress;
import com.ecom.beans.Customer;
import com.ecom.service.CustomerAddressService;
import com.ecom.service.CustomerRegistrationService;

import com.ecom.beans.PhysicalProducts;
import com.ecom.service.PhysicalProductService;
@Controller
@RequestMapping("/")
public class CustomerRegistrationController {
	@Autowired
	CustomerRegistrationService customerservice;
	@Autowired
	CustomerAddressService customeraddressservice;
	@Autowired
	PhysicalProductService physicalProductService;
	@RequestMapping("/")
	public String getMethod() {
		return "index";
	}

	@RequestMapping("/front-end")
	public String getLogin(Model model) {
		
		List<PhysicalProducts> productobject=physicalProductService.getAllProduct();
		model.addAttribute("productlist", productobject);
		
		List<PhysicalProducts> products = physicalProductService.getLatestProducts();
      model.addAttribute("products", products);
	
		return "front-end-index";
	}

	@RequestMapping("/loginpage")
	public String addLogin(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		model.addAttribute("customerObject", customerObject);
		return "front-end-login";
	}

	@RequestMapping("/registration")
	public String addRegistration(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		System.out.println("santhosh");
		model.addAttribute("customerObject", customerObject);
		return "front-end-register";
	}

	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public String saveRegistration(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		System.out.println("I am Save User");

		if (customerObject.getConfirmPassword().equals(customerObject.getPassword())) {
			customerObject.setIsActive('Y');
			customerservice.addCustomer(customerObject);
			System.out.println(customerObject.getFullName());
			return "front-end-login";
		} else {
			model.addAttribute("msg", "password and confirm password should be same ");
			return "redirect:/registration";
		}
	}

	@GetMapping("/customerlogin")
	public String loginValidation(Model model, Customer customerObject, HttpServletRequest request) {

		System.out.println(customerObject.getEmail());
		System.out.println(customerObject.getPassword());
		System.out.println(customerObject.getCustomerId());
		Customer signinObj = customerservice.getCustomer(customerObject.getEmail(), customerObject.getPassword());
		if (signinObj != null) {
			HttpSession session = request.getSession();
			session.setAttribute("customerid", customerObject.getCustomerId());
			session.setAttribute("fullname", customerObject.getFullName());
			session.setAttribute("email", customerObject.getEmail());
			signinObj.setCreated(LocalDate.now());
			signinObj.setUpdated(LocalDate.now());
			return "front-end-dashboard";

		} else {
			Customer customerobject = new Customer();
			model.addAttribute("customerObject", customerobject);

			model.addAttribute("msg", "The entered details are wrong.\t Please check your Email and password");

			return "front-end-login";

		}
	}

	@GetMapping("/customerprofile/{id}")
	public String addAddress(Model model, @PathVariable("id") int customerid) {
		Customer customerobject = customerservice.getCustomerById(customerid);
		CustomerAddress customeraddress = new CustomerAddress();
		System.out.println("santosh");

		model.addAttribute("customerobject", customerobject);
		model.addAttribute("customerid", customerobject.getCustomerId());
		model.addAttribute("password", customerobject.getPassword());
		model.addAttribute("Confirm_password", customerobject.getConfirmPassword());
		model.addAttribute("username", customerobject.getFullName());
		model.addAttribute("customeraddressobject", customeraddress);

		return "front-end-profile";
	}

	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.setAttribute(null, session.getAttributeNames());
		return "front-end-index";
	}

	@RequestMapping("/saveaddressandupdate")
	public String addDetails(Model model, Customer customerObject, CustomerAddress customeraddressObject,
			HttpServletRequest request) {
		customerObject.setIsActive('Y');
		customerservice.addCustomer(customerObject);
		customeraddressObject.setIsActive('Y');
		CustomerAddress addressobject = customeraddressservice.addcustomeraddress(customeraddressObject);
		model.addAttribute("address", addressobject);
		HttpSession session = request.getSession();
		session.setAttribute("addressid", addressobject.getAddressId());
		return "front-end-dashboard";
	}

	@GetMapping("/deleteprofile/{id}")
	public String deleteProfile(Model model, @PathVariable("id") int customerid,
			@ModelAttribute(value = "customerObject") Customer customerObject) {
		customerservice.deleteBydataId(customerid);
		return "redirect:/registration";
	}

	@RequestMapping("/editpassword/{id}")
	public String changepassword(Model model, @PathVariable("id") int customerid) {
		Customer customerobject = customerservice.getCustomerById(customerid);
		model.addAttribute("customerobject", customerobject);
		model.addAttribute("customerid", customerobject.getCustomerId());
		model.addAttribute("password", customerobject.getPassword());
		model.addAttribute("ConfirmPassword", customerobject.getConfirmPassword());
		model.addAttribute("FullName", customerobject.getFullName());
		model.addAttribute("Email", customerobject.getEmail());
		model.addAttribute("MobileNumber", customerobject.getMobileNumber());
		model.addAttribute("FirstName", customerobject.getFirstName());
		model.addAttribute("LastName", customerobject.getLastName());
		customerobject.setIsActive('y');

		return "forget-password";
	}

	@RequestMapping("/savepassword")
	public String addDetails(Model model, @ModelAttribute(value = "customerobject") Customer customerobject,
			HttpServletRequest request) {
		String ConfirmPassword = customerobject.getConfirmPassword();
		if (customerobject.getPassword().equals(ConfirmPassword)) {
			customerobject.setIsActive('y');
			Customer object = customerservice.addCustomer(customerobject);
			HttpSession session = request.getSession();
			session.setAttribute("customerid", object.getCustomerId());
			return "front-end-dashboard";
		} else {
			model.addAttribute("customerobject", customerobject);
			model.addAttribute("msg", "password and confirm password should be same ");
			return "forget-password";
		}
	}

	@RequestMapping("/editcontactdetails/{id}")
	public String editprofile(Model model, @PathVariable(value = "id") int customerid) {
		Customer customerdetailsbyid = customerservice.getCustomerById(customerid);
		model.addAttribute("customerdetailsbyid", customerdetailsbyid);
		return "edit-customer-details";
	}

	@RequestMapping("/updatecontactdetails")
	public String updatedetails(Model model, Customer customer) {
		customer.setIsActive('Y');
		Customer customerobject = customerservice.addCustomer(customer);
		model.addAttribute("customerobject", customerobject);
		return "front-end-dashboard";
	}

	@RequestMapping("/editaddress/{id}")
	public String editaddress(Model model, @PathVariable(value = "id") int id) {
		CustomerAddress addressobject = customeraddressservice.getcustomeraddressById(id);
		model.addAttribute("addressobject", addressobject);
		return "edit-customer-address";
	}
}