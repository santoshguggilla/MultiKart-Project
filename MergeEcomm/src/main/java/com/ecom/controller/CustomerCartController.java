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
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.beans.Customer;
import com.ecom.beans.CustomerCart;
import com.ecom.beans.CustomerOrder;
import com.ecom.beans.PhysicalCategory;
import com.ecom.beans.PhysicalProducts;
import com.ecom.beans.PhysicalSubCategory;
import com.ecom.repository.CustomerCartRepository;
import com.ecom.repository.CustomerOrderRepository;
import com.ecom.repository.PhysicalProductRepository;
import com.ecom.service.CustomerCartService;
import com.ecom.service.CustomerService;
import com.ecom.service.PhysicalCategoryService;
import com.ecom.service.PhysicalProductService;
import com.ecom.service.PhysicalSubCategoryService;
import com.ecom.service.SubcategoryService;

@Controller
@RequestMapping("/cart")
public class CustomerCartController {
	@Autowired
	PhysicalProductService physicalProductService;
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	@Autowired
	CustomerCartService customerCartService;
	@Autowired
	CustomerService customerservice;
	@Autowired
	PhysicalCategoryService PhysicalCategoryServices;
	@Autowired
	SubcategoryService subcatagoryservice;
	@Autowired
	PhysicalProductService physicalproductservice;
	@Autowired
	PhysicalSubCategoryService PhysicalSubCategoryServices;
	@Autowired
	PhysicalProductRepository physicalProductRepository;
	@Autowired
	CustomerCartRepository customerCartRepository;

	@RequestMapping("/cartloginpage")
	public String addCartLogin(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		model.addAttribute("customerObject", customerObject);
		return "front-end-Cartlogin";
	}

	@RequestMapping("/frontendcart")
	public String addCart(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		System.out.println("l");
		model.addAttribute("customerObject", customerObject);
		return "Cartlogin";
	}

	@RequestMapping("/Cartregistration")
	public String addCartRegistration(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		System.out.println("santhosh");
		model.addAttribute("customerObject", customerObject);
		return "front-end-Cartregister";
	}

	@RequestMapping(value = "/savecartCustomer", method = RequestMethod.POST)
	public String saveCartRegistration(Model model, @ModelAttribute(value = "customerObject") Customer customerObject) {
		System.out.println("I am Save User");
		// String str1=(customerObject.getDOB())
		if (customerObject.getConfirmPassword().equals(customerObject.getPassword())) {
			customerObject.setIsActive('y');
			customerservice.addCustomer(customerObject);
			System.out.println(customerObject.getUserName());
			return "redirect:/cart/frontendcart";
		} else {
			model.addAttribute("msg", "password and confirm password should be same ");
			return "redirect:/cart/Cartregistration";
		}
	}

	@GetMapping("/customercartlogin")
	public String loginValidationcart(Model model, @ModelAttribute(value = "customerObject") Customer customerObject,
			HttpServletRequest request) {
		System.out.println(customerObject.getEmail());
		System.out.println(customerObject.getPassword());
		System.out.println(customerObject.getCustomerId());
		Customer signinObj = customerservice.getCustomer(customerObject.getEmail(), customerObject.getPassword());
		if (signinObj != null) {
			HttpSession session = request.getSession();
			session.setAttribute("customerid", customerObject.getCustomerId());
			session.setAttribute("fullname", customerObject.getUserName());
			session.setAttribute("email", customerObject.getEmail());
			signinObj.setCreated(LocalDate.now());
			signinObj.setUpdated(LocalDate.now());
			return "redirect:/cart/frontendproducts/" + signinObj.getCustomerId();
		}

		else {
			Customer cObject = new Customer();
			model.addAttribute("customerObject", cObject);
			model.addAttribute("msg", "The entered details are wrong.\t Please check your Email and password");
			return "redirect:/cart/frontendcart";
		}
	}

	@RequestMapping("/frontendproducts/{id}")
	public String method(Model model, @PathVariable(value = "id") int id) {

		Customer object = customerservice.getCustomerById(id);
		model.addAttribute("customer", object);
		List<PhysicalProducts> products = physicalProductRepository.findLatestProducts();
		model.addAttribute("product", products);

		List<PhysicalCategory> catagorylist = PhysicalCategoryServices.getAllCategory();
		model.addAttribute("catagorylist", catagorylist);

		List<PhysicalSubCategory> subcatagorylist = PhysicalSubCategoryServices.list();
		model.addAttribute("subcatagorylist", subcatagorylist);
		List<PhysicalProducts> productobject = physicalproductservice.getAllProduct();
		model.addAttribute("productlist", productobject);
		return "front-end-products";
	}

	@RequestMapping("/addtocart/{id}/{cid}")
	public String addcart(@PathVariable(value = "id") int id, CustomerCart cart, @PathVariable(value = "cid") int cid) {
		PhysicalProducts product = physicalProductService.getProductById(id);
		Customer customer = customerservice.getCustomerById(cid);
		cart.setImage(product.getProductImage());
		cart.setCustomerId(cid);
		cart.setProductId(product.getProductId());
		cart.setProductName(product.getProductName());
		cart.setProductModelNumber(product.getProductModelNumber());
		cart.setProductPrice(product.getProductMRPPrice());
		cart.setProductCompany(product.getProductCompany());
		cart.setProductCode(product.getProductCode());
		cart.setIsActive('Y');
		customerCartService.addCart(cart);
		return "redirect:/cart/frontendproducts/" + customer.getCustomerId();
	}

	@RequestMapping("/cartList/{cid}")
	public String cartList(Model model, CustomerCart cart, @PathVariable("cid") int cid) {
		List<CustomerCart> list = customerCartRepository.getCartActiveList(cid);
		Customer data = customerservice.getCustomerById(cid);
		double totalcartprice=customerCartRepository.getcarttotal(cid);
		model.addAttribute("totalcartprice", totalcartprice);
		model.addAttribute("Cart", list);
		model.addAttribute("customer", data);
		
		return "cart";
	}

	@RequestMapping("/deleteCart/{id}")
	public String deleteCart(Model model, @PathVariable("id") int id) {

		System.out.println("ssss");
		customerCartService.deleteBydataId(id);

		return "redirect:/cart/cartList";

	}

	@RequestMapping("/addquatity/{id}/{cid}")
	public String totalprice(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "cid") int cid) {

		int h = 0;
		CustomerCart cartId = customerCartService.getCartById(id);
		Customer customer = customerservice.getCustomerById(cid);
//		List<PhysicalProducts> modelnumberlist = physicalProductRepository
//				.getPhysicalProductsByModelNumber(cartId.getProductModelNumber());
//		if (modelnumberlist.size() >= quantity) {
//			for (PhysicalProducts product : modelnumberlist) {
//				for (int i = h; i < quantity - 1; i++) {
//					CustomerCart cart = new CustomerCart();
//					cart.setImage(product.getProductImage());
//					cart.setProductId(product.getProductId());
//					cart.setProductName(product.getProductName());
//					cart.setProductModelNumber(product.getProductModelNumber());
//					cart.setProductPrice(product.getProductMRPPrice());
//					cart.setProductCompany(product.getProductCompany());
//					cart.setProductCode(product.getProductCode());
//					cart.setIsActive('N');
//					customerCartService.addCart(cart);
//					i = quantity;
//					h++;
//				}
//
//			}
//
//		} else {
//
//			quantity = modelnumberlist.size();
//
//			for (PhysicalProducts product : modelnumberlist) {
//				for (int i = h; i < quantity - 1; i++) {
//					CustomerCart cart = new CustomerCart();
//					cart.setImage(product.getProductImage());
//					cart.setProductId(product.getProductId());
//					cart.setProductName(product.getProductName());
//					cart.setProductModelNumber(product.getProductModelNumber());
//					cart.setProductPrice(product.getProductMRPPrice());
//					cart.setProductCompany(product.getProductCompany());
//					cart.setProductCode(product.getProductCode());
//					cart.setIsActive('N');
//					customerCartService.addCart(cart);
//					i = quantity;
//					h++;
//				}
//
//			}
//		}
//		model.addAttribute("msg", "only " + " " + modelnumberlist.size() + " are avaliable");
//		
		Double total = cartId.getProductPrice();
		cartId.setTotalprice(total);
		customerCartService.addCart(cartId);

		return "redirect:/cart/cartList/" + customer.getCustomerId();
	}

}

