package com.ecom.controller;

 

 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.beans.CustomerCart;
import com.ecom.beans.PhysicalProducts;
import com.ecom.service.CustomerCartService;
import com.ecom.service.PhysicalProductService;

@Controller
@RequestMapping("/cart")
public class CustomerCartController {
	@Autowired
	PhysicalProductService physicalProductService;
	
  @Autowired
  CustomerCartService customerCartService;
  
  @RequestMapping("/addtocart/{id}")
  public String addcart(@PathVariable(value="id") int id,CustomerCart cart) {
	  PhysicalProducts product=physicalProductService.getProductById(id);
	  cart.setImage(product.getImage()); 
	  cart.setProductId(product.getProductId());
	  cart.setProductName(product.getProductName());
	  cart.setProductPrice(product.getProductPrice());
	  cart.setProductCompany(product.getProductCompany());
	  cart.setProductCode(product.getProductCode());
	  customerCartService.addCart(cart);
	  return "redirect:/cart/cartList/"+product.getProductId();
  }
  
  @RequestMapping("/cartList/{id}")
  public String cartList(Model model,CustomerCart cart,@PathVariable("id")int productid)
  {
	  List<CustomerCart > list=customerCartService. getAllCart();
	  
	  model.addAttribute("Cart", list);
	
	  System.out.println("AddCart");
	  return"cart";
	  
  }
  @RequestMapping("/deleteCart/{id}")
	public String deleteCart(Model model, @PathVariable("id") int id) {

		System.out.println("ssss");
		customerCartService.deleteBydataId(id);

		return "redirect:/cart/cartList";

	}
	
  
  

}
