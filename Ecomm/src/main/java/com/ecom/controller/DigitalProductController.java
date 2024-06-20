package com.ecom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecom.beans.DigitalProducts;
import com.ecom.repository.DigitalProductsRepository;
import com.ecom.service.DigitalProductsService;

@Controller
@RequestMapping("/Dgproducts")
public class DigitalProductController {
	@Autowired
	DigitalProductsService digitalproductservice;
	
	@Autowired
	DigitalProductsRepository digirepository;
	
	@GetMapping("/create-product")
	public String getProducts(Model model, @ModelAttribute(value = "vendorObj") DigitalProducts digitalproduct) {
System.out.println("laddu");
		model.addAttribute("digitalproduct", digitalproduct);
		model.addAttribute("digitalproductid", digitalproduct.getProductId());
		return "add-digital-product";

	}
@RequestMapping("/savedigitalproducts")
public String saveDigitalProducts(Model model, DigitalProducts object) {
	object.setIsActive('Y');
	DigitalProducts digitalobject=digitalproductservice.addProduct(object);
	model.addAttribute("digitalproduct", digitalobject);
	return "digital-image";
}
 

	private static final String folderPath = "C:\\Users\\Aakash\\Desktop\\santosh_job_practice\\Ecomm\\src\\main\\resources\\static\\Digitaluploads\\";

	@RequestMapping(value = "/uploadImage/{id}", method = RequestMethod.POST)
	public String saveUser(@RequestParam("files") MultipartFile[] file,HttpSession session,
			@PathVariable(value = "id") int productid,Model model,  DigitalProducts digObj)
			throws IOException {
		DigitalProducts object = digitalproductservice.getProductById(productid);
	

		System.out.println(object);
		StringBuilder filejoin = new StringBuilder();

		
		Integer a = object.getProductId();
		String UploadDir = folderPath+ a;
	
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
			object.setImages("../Digitaluploads/"+a+"/"+f.getOriginalFilename());

		}
		
		//object.setImage(file);
		//object.setIsactive('s');
		object.setCreated(LocalDate.now());
		object.setUpdated(LocalDate.now());
		DigitalProducts savedUser = digirepository.save(object);
		System.out.println(savedUser);
		return "redirect:/Dgproducts/showlist";
	}

 
	@GetMapping("/showlist")
	public String ShowDigitalproductList(Model model) {
		System.out.println("kushi");
  		List<DigitalProducts> digitalproductlist = digitalproductservice.getAllDigitalProductsData();
		model.addAttribute("digitalproductlist", digitalproductlist);
		return "digital-product-list";
	}

	@RequestMapping("/search")
	public String categoryAdmin(DigitalProducts digitalProducts, Model model, String keyword) {
		if (keyword != null) {

			List<DigitalProducts> digitalproductlist = digitalproductservice.getDigitalByName(keyword);
			model.addAttribute("digitalproductlist", digitalproductlist);
		
			return "digital-product-list";
		} else {
			List<DigitalProducts> digitalproductlist = digitalproductservice.getAllDigitalProductsData();
			model.addAttribute("digitalproductlist", digitalproductlist);
			return "digital-product-list";
		}
	}

	@GetMapping("/deleteDigitalProduct/{id}")
	public String deleteDigitalProducts(Model model, @PathVariable("id") int id) {

		
		digitalproductservice.deleteBydataId(id);
		
		return "redirect:/Dgproducts/showlist";

	}

	@GetMapping("/editproducts/{id}")
	public String EditByProductId(Model model, @PathVariable("id") int id) {
		DigitalProducts digitalproductbyid = digitalproductservice.getDetails(id);
		model.addAttribute("digitalproductbyid", digitalproductbyid);
		return "edit-digital-product";
	}

	 
	
	
}
