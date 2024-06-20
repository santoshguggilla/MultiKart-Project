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
import org.springframework.web.servlet.ModelAndView;

import com.ecom.beans.Admin;
import com.ecom.beans.Customer;
import com.ecom.beans.CustomerAddress;
import com.ecom.service.AdminService;
import com.ecom.service.CustomerAddressService;
import com.ecom.service.CustomerRegistrationService;

@Controller
@RequestMapping("/emp")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	CustomerRegistrationService customerRegistrationService;
	@Autowired
    CustomerAddressService customerAddressService;

	@RequestMapping(value = "/back-end")
	public String getBackEndIndex(Model model, @ModelAttribute(value = "employee") Admin admin) {

		return "back-end-index-login";

	}

	@RequestMapping(value = "/back-end-Reg")
	public String getBackEndReg(Model model, @ModelAttribute(value = "employee") Admin admin) {

		return "back-end-Register";

	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ModelAndView addEmployee(Model model, @ModelAttribute(value = "employee") Admin admin, HttpSession session) {

		Admin adminObj = adminService.findByEmailId(admin.getEmployeeMail());

		if (adminObj != null) {
			ModelAndView modelAndView = new ModelAndView("redirect:/emp/back-end-Reg");
			String errorMessage = "User Already Exists";
			modelAndView.addObject("errorMessage", errorMessage);
			modelAndView.addObject("employee", admin);

			return modelAndView;
		} else {

			admin.setIsActive('y');
			admin.setCreated(LocalDate.now());
			admin.setUpdated(LocalDate.now());
			ModelAndView modelAndView = new ModelAndView("back-end-dashboard");
			Admin adminobject = adminService.addEmployeeDetails(admin);
			session.setAttribute("name", admin.getEmployeeName());
			session.setAttribute("role", admin.getEmployeeDesignation());

			model.addAttribute("admin", adminobject);
			return modelAndView;

		}
	}

	@RequestMapping(value = "/newemployeelogin", method = RequestMethod.POST)
	public String newemployeelogin(Model model, Admin admin, HttpServletRequest request, HttpSession session1) {
		Admin adminobject = adminService.getEmployeeDetails(admin.getEmployeeMail(), admin.getEmployeePassword());

		if (adminobject != null) {

			HttpSession session = request.getSession();
			session.setAttribute("email", adminobject.getEmployeeMail());
			session.setAttribute("employeeId", adminobject.getEmployeeId());
			System.out.println(adminobject.getEmployeeId());
			session.setAttribute("name", adminobject.getEmployeeName());
			session.setAttribute("role", adminobject.getEmployeeDesignation());
			session.setAttribute("mobile", adminobject.getEmployeeMobileNumber());

			session.setAttribute("id", adminobject.getEmployeeId());
			model.addAttribute("admin", adminobject);

			return "back-end-dashboard";
		} else {

			Admin adminobjectone = new Admin();
			System.out.println(adminobjectone);

			String errorMessage = "Invalid Credentials!";
			model.addAttribute("errorMessage", errorMessage);

			return "back-end-index-login";
		}
	}

	@RequestMapping("/EditProfile/{id}")
	public String EditEmployee(Model model, @PathVariable("id") int employeeId) {

		Admin Adminobject = adminService.getEmployeeDetailsById(employeeId);
		System.out.println("inside getAdminById id is:::" + Adminobject.getEmployeeId());
		model.addAttribute("Adminobject", Adminobject);
		model.addAttribute("employeeId", employeeId);

		return "edit-profile";
	}

	@RequestMapping("/profile")
	public String profile(Model model, @ModelAttribute(value = "Adminobject") Admin admin) {
		admin.setIsActive('y');
		Admin adminobject = adminService.addEmployeeDetails(admin);
		model.addAttribute("adminobject", adminobject);

		return "backend-profile";
	}

	@GetMapping("/delete-profile/{id}")
	public String deleteCoupon(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		adminService.deleteEmployeeDetailsById(id);

		return "redirect:/emp/back-end";

	}

	@GetMapping("/create")
	public String createUser() {
		return "back-end-create-user";
	}

	@GetMapping(value = "/adminlist")
	public String getAllAdmins(Model model) {
		List<Admin> adminlist = adminService.getAllEmployeeDetails();
		model.addAttribute("adminlist", adminlist);

		return "admin-list";

	}

	@GetMapping(value = "/userlist")
	public String getAllUsers(Model model) {
		List<Customer> userlist = customerRegistrationService.getAllCustomerRegistration();
		model.addAttribute("userlist", userlist);

		return "user-list";
	}

	@GetMapping("/delete-admin/{id}")
	public String deleteAdmin(Model model, @PathVariable int id) {
		System.out.println("..." + id);
		adminService.deleteEmployeeDetailsById(id);
		return "redirect:/emp/adminlist";

	}

	@GetMapping("/delete-user/{id}")
	public String deleteUser(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		customerRegistrationService.deleteBydataId(id);
		return "redirect:/emp/userlist";

	}
	@RequestMapping("/user-profile/{id}")
	public String viewUserProfile(Model model,@PathVariable("id") int customerid,HttpSession session) {
		
		Customer customerobject = customerRegistrationService.getCustomerById(customerid);
		CustomerAddress customeraddress = customerAddressService.getcustomeraddressById(customerid);
		
		session.setAttribute("name", customerobject.getFirstName());
		session.setAttribute("mail", customerobject.getEmail());
		
		session.setAttribute("address", customeraddress.getAddressLine1());
		session.setAttribute("address1", customeraddress.getAddressLine2());
		session.setAttribute("city", customeraddress.getCity());
		session.setAttribute("pincode", customeraddress.getPinCode());
		session.setAttribute("number", customeraddress.getMobileNumber());
		
		return "user-list-profile";
	}

}
