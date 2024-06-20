package com.ecom.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.beans.Admin;
import com.ecom.beans.Coupon;
import com.ecom.repository.CouponRepository;
import com.ecom.service.AdminService;
import com.ecom.service.CouponService;

@Controller
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	CouponService couponService;

	@Autowired
	CouponRepository couponRepository;
	
	@Autowired
	AdminService adminService;



	@RequestMapping("/addcoupon")
	public String addCoupon(Model model, @ModelAttribute(value = "couponobject") Coupon couponobject,HttpSession session) {
		
		model.addAttribute("couponobject", couponobject);
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		}
		else {
		Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
		model.addAttribute("admin", admin);
		}
		return "add-coupon";
	}

	@RequestMapping(value = "/savecoupon", method = RequestMethod.POST)
	public String saveCoupon(Model model,@RequestParam ("file") MultipartFile file, @ModelAttribute(value = "couponobject") Coupon coupon,HttpSession session) throws IOException {
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			coupon.setCreatedBy(0);
			coupon.setUpdatedBy(0);
		}
		else {
			coupon.setCreatedBy(sessionAdmin.getEmployeeId());
			coupon.setUpdatedBy(sessionAdmin.getEmployeeId());
		}

		if(file.getOriginalFilename()=="") {
			
			coupon.setImage(coupon.getImage());
		}else {

			coupon.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		}
		coupon.setIsActive('Y');
		coupon.setCreated(LocalDate.now());
		coupon.setUpdated(LocalDate.now());
		couponService.addCoupon(coupon);


		return "redirect:/coupon/couponlist";
	}


	@RequestMapping("/couponlist")
	public String couponList(Model model, Coupon coupon,HttpSession session) {
		List<Coupon> list = couponService.getAllCoupon();
		List<Coupon> couponlist=new ArrayList<>();
		for(Coupon coup:list) {
			if(coup.getIsActive()=='Y') {
				couponlist.add(coup);
			}
		}
		model.addAttribute("couponlist", couponlist);
		
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		}
		else {
		Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
		model.addAttribute("admin", admin);
		}
		return "coupon-list";
	}

	@GetMapping("/edit-coupon/{id}")
	public String getCouponById(Model model, @PathVariable("id") int id,HttpSession session) {
		Coupon couponObj = couponService.getCouponById(id);
		model.addAttribute("getimage", couponObj.getImage());
		model.addAttribute("couponObj", couponObj);
		Admin sessionAdmin = (Admin)session.getAttribute("adminObj");
		if(sessionAdmin==null) {
			Admin admin = adminService.getEmployeeDetailsById(1);
			model.addAttribute("admin", admin);
		}
		else {
		Admin admin = adminService.getEmployeeDetailsById(sessionAdmin.getEmployeeId());
		model.addAttribute("admin", admin);
		}
		return "edit-coupon";
	}

	@GetMapping("/delete-coupon/{id}")
	public String deleteCoupon(Model model, @PathVariable("id") int id) {
		Coupon couponObj = couponService.getCouponById(id);
		couponObj.setIsActive('N');
		couponService.addCoupon(couponObj);
		return "redirect:/coupon/couponlist";
	}






}
