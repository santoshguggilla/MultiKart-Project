package com.ecom.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.beans.Admin;
import com.ecom.beans.Customer;
import com.ecom.beans.CustomerAddress;
import com.ecom.beans.PhysicalProducts;
import com.ecom.service.AdminService;
import com.ecom.service.CustomerAddressService;
import com.ecom.service.CustomerService;
import com.ecom.service.PhysicalProductService;
import com.ecom.utilities.Utilities;

@Controller
@RequestMapping("/emp")
public class AdminController {

	@Autowired
	AdminService adminService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerAddressService customerAddressService;
	@Autowired
	PhysicalProductService physicalProductService;

	@RequestMapping(value = "/back-end")
	public String getBackEndIndex(Model model, @ModelAttribute(value = "employee") Admin admin) {

		return "back-end-index-login";

	}

	@RequestMapping(value = "/back-end-Reg")
	public String getBackEndReg(Model model, @ModelAttribute(value = "employee") Admin admin) {

		return "back-end-Register";

	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public String addEmployee(Model model, @ModelAttribute(value = "employee") Admin admin, HttpSession session) {

		Admin adminObj = adminService.findByEmailId(admin.getEmployeeMail());

		if (adminObj != null) {
			session.setAttribute("adminObj", adminObj);
			String errorMessage = "User Already Exists!";
			model.addAttribute("errorMessage", errorMessage);

			return "back-end-Register";
		} else {

			String strEncPassword = Utilities.getEncryptSecurePassword(admin.getEmployeePassword(), "Ecom");
			admin.setEmployeePassword(strEncPassword);

			admin.setIsActive('Y');
			admin.setCreated(LocalDate.now());
			admin.setUpdated(LocalDate.now());
			admin.setCreatedBy(admin.getEmployeeId());
			admin.setUpdatedBy(admin.getEmployeeId());

			// ModelAndView modelAndView = new ModelAndView("back-end-dashboard");
			Admin adminobject = adminService.addEmployeeDetails(admin);

			model.addAttribute("admin", adminobject);
			return "back-end-dashboard";

		}
	}

	@RequestMapping(value = "/newemployeelogin", method = RequestMethod.POST)
	public String newemployeelogin(Model model, Admin admin, HttpServletRequest request, HttpSession session) {

		String strEncPassword = Utilities.getEncryptSecurePassword(admin.getEmployeePassword(), "Ecom");

		Admin adminobject = adminService.getEmployeeDetails(admin.getEmployeeMail(), strEncPassword);

		if (adminobject != null) {

			session.setAttribute("adminObj", adminobject);
			Admin sessionAdmin = (Admin) session.getAttribute("adminObj");

			model.addAttribute("admin", adminobject);

			return "back-end-dashboard";
		} else {

			String errorMessage = "Invalid Credentials!";
			model.addAttribute("errorMessage", errorMessage);

			return "back-end-index-login";
		}
	}

	@RequestMapping("/EditProfile/{id}")
	public String EditEmployee(Model model, @PathVariable("id") int employeeId) {

		Admin Adminobject = adminService.getEmployeeDetailsById(employeeId);

		model.addAttribute("Adminobject", Adminobject);
		model.addAttribute("employeeId", employeeId);

		return "edit-profile";
	}

	@RequestMapping("/profile")
	public String profile(Model model, @RequestParam("file") MultipartFile file,
			@ModelAttribute(value = "Adminobject") Admin admin) throws IOException {

		if (file.getOriginalFilename() == "") {

			admin.setImage(admin.getImage());
		} else {

			admin.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		}
		admin.setIsActive('Y');
		admin.setCreated(LocalDate.now());
		admin.setUpdated(LocalDate.now());
		Admin adminobject = adminService.addEmployeeDetails(admin);
		model.addAttribute("adminobject", adminobject);

		return "backend-profile";
	}

	@GetMapping("/delete-profile/{id}")
	public String deleteProfile(Model model, @PathVariable("id") int id) {

		Admin admin = adminService.getEmployeeDetailsById(id);
		admin.setIsActive('N');
		adminService.addEmployeeDetails(admin);

		return "redirect:/emp/back-end";

	}

	@GetMapping("/create")
	public String createUser() {
		return "back-end-create-user";
	}

	@GetMapping(value = "/adminlist")
	public String getAllAdmins(Model model, HttpSession session) {
		List<Admin> list = adminService.getAllEmployeeDetails();

		List<Admin> adminlist = new ArrayList<>();
		for (Admin admin : list) {
			if (admin.getIsActive() == 'Y' || admin.getIsActive() == 'y') {
				adminlist.add(admin);
			}
		}
		model.addAttribute("adminlist", adminlist);

		Admin sessionAdmin = (Admin) session.getAttribute("adminObj");
		if (sessionAdmin == null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		} else {
			Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
			model.addAttribute("admin", admin);
		}

		return "admin-list";

	}

	@GetMapping(value = "/userlist")
	public String getAllUsers(Model model, HttpSession session) {
		List<Customer> list = customerService.getAllCustomerRegistration();

		List<Customer> userlist = new ArrayList<>();
		for (Customer customer : list) {
			if (customer.getIsActive() == 'Y' || customer.getIsActive() == 'y') {
				userlist.add(customer);
			}
		}

		model.addAttribute("userlist", userlist);

		Admin sessionAdmin = (Admin) session.getAttribute("adminObj");
		if (sessionAdmin == null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		} else {
			Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
			model.addAttribute("admin", admin);
		}

		return "user-list";
	}

	@GetMapping("/delete-admin/{id}")
	public String deleteAdmin(Model model, @PathVariable int id) {

		Admin admin = adminService.getEmployeeDetailsById(id);
		admin.setIsActive('N');
		adminService.addEmployeeDetails(admin);

		return "redirect:/emp/adminlist";

	}

	@GetMapping("/delete-user/{id}")
	public String deleteUser(Model model, @PathVariable("id") int id) {

		Customer customer = customerService.getCustomerById(id);
		customer.setIsActive('N');
		customerService.addCustomer(customer);
		return "redirect:/emp/userlist";

	}

	@RequestMapping("/user-profile/{id}")
	public String viewUserProfile(Model model, @PathVariable("id") int customerid, HttpSession session) {

		Customer customerobject = customerService.getCustomerById(customerid);
		CustomerAddress customeraddress = customerAddressService.getcustomeraddressById(customerid);
		model.addAttribute("customerobject", customerobject);
		model.addAttribute("customeraddress", customeraddress);
		return "user-list-profile";
	}

	@RequestMapping("/productdetails/{id}")
	public String getProductdetailsByProductId(Model model, @PathVariable("id") int id, HttpSession session) {
		PhysicalProducts productlist = physicalProductService.getProductById(id);
		model.addAttribute("productlist", productlist);
		return "front-end-product-detail";
	}

	@RequestMapping("/aboutus")
	public String getAboutUs(Model model) {

		List<Admin> adminlist = adminService.getAllRoles();
		model.addAttribute("adminlist", adminlist);

		List<Admin> adminDesigner = adminService.getAllDesigners();
		model.addAttribute("adminDesigner", adminDesigner);

		return "about-us";
	}

	@RequestMapping("/back-end-profile/{id}")
	public String getProfile(Model model, @PathVariable("id") int id,
			@ModelAttribute(value = "Adminobject") Admin admin) {

		Admin adminobject = adminService.getEmployeeDetailsById(id);

		model.addAttribute("adminobject", adminobject);
		return "backend-profile";
	}

}
