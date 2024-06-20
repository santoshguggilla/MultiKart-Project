package com.ecom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.beans.Coupon;

import com.ecom.repository.CouponRepository;
import com.ecom.service.CouponService;

@Controller
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	CouponService couponService;

	@Autowired
	CouponRepository couponRepository;

	@RequestMapping("/coupondetails")
	public String couponDetailsCreation(Model model, @ModelAttribute(value = "couponobject") Coupon couponobject) {
		model.addAttribute("couponobject", couponobject);
		return "add-coupon";

	}

	@RequestMapping("/couponsphotos")
	public String photomethod(Model model, @ModelAttribute(value = "couponobject") Coupon couponobject,
			HttpServletRequest request) {

		couponobject.setIsActive('y');
		couponobject.setCreated(LocalDate.now());
		couponobject.setUpdated(LocalDate.now());

		Coupon couponobjectone = couponService.addCoupon(couponobject);
		model.addAttribute("couponobject", couponobjectone);

		System.out.println(couponobject.getId());
		return "upload-view-coupon";
	}

	public static String uploadDirectory = "C:\\Users\\Aakash\\Desktop\\santosh_job_practice\\Ecomm\\src\\main\\resources\\static\\coupon\\";

	@RequestMapping(value = "/couponimage/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String fileUpload(@RequestParam("files") MultipartFile[] file, HttpSession session,
			@PathVariable(value = "id") int couponid, Coupon couponobject) throws IOException {

		Coupon coupon = couponService.getCouponById(couponid);
		System.out.println(coupon);
		StringBuilder filejoin = new StringBuilder();

		Integer couponId = coupon.getId();
		String UploadDir = uploadDirectory + couponId;

		
		for (MultipartFile f : file) {

			filejoin.append(f.getOriginalFilename() + ",");
			String fileName = StringUtils.cleanPath(f.getOriginalFilename());
			Path uploadPath = Paths.get(UploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputstream = f.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputstream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
			coupon.setImage("../coupon/" + couponId + "/" + f.getOriginalFilename());

		}

		Coupon savedUser = couponRepository.save(coupon);
		System.out.println(savedUser);
		return "redirect:/coupon/couponlist";

	}

	@RequestMapping("/couponlist")
	public String method(Model model, Coupon coupon) {
		List<Coupon> couponlist = couponService.getAllCoupon();
		model.addAttribute("couponlist", couponlist);
		model.addAttribute("couponId", coupon.getId());
		return "coupon-list-new-one";
	}

	@GetMapping("/edit-coupon/{id}")
	public String getCouponById(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		Coupon couponObj = couponService.getCouponById(id);
		model.addAttribute("couponObj", couponObj);
		return "edit-coupon";
	}

	@GetMapping("/delete-coupon/{id}")
	public String deleteCoupon(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		couponService.deleteCouponById(id);

		List<Coupon> couponlist = couponService.getAllCoupon();

		model.addAttribute("couponlist", couponlist);

		return "redirect:/coupon/couponlist";

	}

}
