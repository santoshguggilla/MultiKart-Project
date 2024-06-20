package com.ecom.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.beans.BilingDetails;
import com.ecom.beans.Customer;
import com.ecom.beans.CustomerCart;
import com.ecom.beans.Order;
import com.ecom.repository.CustomerCartRepository;
import com.ecom.service.BillingService;
import com.ecom.service.CustomerCartService;
import com.ecom.service.CustomerService;
import com.ecom.service.OrderService;

@Controller
@RequestMapping("/Bill")
public class BillingController {

	@Autowired
	BillingService billingService;
	@Autowired
	CustomerCartService customerCartService;
	@Autowired
	OrderService orderService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerCartRepository customerCartRepository;

	@RequestMapping(value = "/indexBill/{cid}")
	public String getIndex(Model model, BilingDetails bilingDetails, @PathVariable(value = "cid") int cid) {
		Customer customerobject = customerService.getCustomerById(cid);
		List<CustomerCart> cartlist = customerCartRepository.getCartActiveList(cid);
		double total=customerCartRepository.getcarttotal(cid);
		model.addAttribute("customer", customerobject);
		model.addAttribute("objBilling", bilingDetails);
		model.addAttribute("total", total);
		model.addAttribute("cartlist", cartlist);
		return "checkout";

	}

	@RequestMapping(value = "/AddBill")
	public String addBill(Model model, @ModelAttribute(value = "objBilling") BilingDetails bilingDetails,
			HttpSession session, Order order) {

//		int min = 10000;
//		int max = 99999;
//		Generate random
//		int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
//		order.setOrderId(random_int);
//
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTVWXYZ1234567890";
//		StringBuilder salt = new StringBuilder();
//		Random rnd = new Random();
//		while (salt.length() < 11) { // length of the random string.
//			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//			salt.append(SALTCHARS.charAt(index));
//		}
//		String saltStr2 = salt.toString();
//		order.setTransactionId(saltStr2);
//
//		long h = 100000000;
//		long g = 999999999;
//		Generate random
//		long random_int1 = (long) Math.floor(Math.random() * (g - h + 1) + h);
//		order.setCarrierNo(random_int1);
//
//		int k = 1000;
//		int d = 99999;
//		int random_int3 = (int) Math.floor(Math.random() * (d - k + 1) + k);
//		order.setOrderNo(random_int3);
//
//		order.setOrderDate(LocalDate.now());
//		order.setLocalTime(LocalTime.now());
//		orderService.addOrderName(order);
//
//		billingService.addBilingName(bilingDetails);
//
//		System.out.println("Add Billing");

		return "redirect:/Bill/BillList";
	}

//	@RequestMapping(value="/SuccessPage")
//	public String success(Model model,@ModelAttribute(value="objBilling") BilingDetails bilingDetails,HttpSession session) {
//		
//		
//		   
//			 
//		
//		return "redirect:/Bill/order-success";
//	}

//	@GetMapping(value="/SuccessPage")
//	public String getAllAdmins(Model model) {
//		List<BilingDetails> list = physicalProductService.getAllBillDetails();
//		
//		model.addAttribute("Billlist", list);
//	
//		return "";
//		
//	}

	@RequestMapping("/BillList")
	public String billList(Model model, BilingDetails bilingDetails) {

//		List<CustomerCart> list = customerCartService.getAllCart();
//
//		model.addAttribute("Cart", list);
//
//		List<Order> listCust2 = orderService.getAllOrderDetails();
//		model.addAttribute("order", listCust2);
//
//		List<BilingDetails> listCust = billingService.getAllBillDetails();
//		model.addAttribute("Name", listCust);
//
//		System.out.println("AddCart");

		return "order-success";

	}

	@RequestMapping("/tracking")
	public String tracking(Model model, BilingDetails bilingDetails) {
//		List<CustomerCart> list = customerCartService.getAllCart();
//
//		model.addAttribute("Tra", list);

		// List<Order> listCust2= orderService.getAllOrderDetails();
//		  model.addAttribute("order", listCust2);
//			
//			model.addAttribute("Track", listCust2);

//		List<BilingDetails> listCust = billingService.getAllBillDetails();
	//	model.addAttribute("Track", listCust);
		return "order-tracking";
	}

}
