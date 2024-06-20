package com.ecom.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.ecom.beans.PhysicalProducts;
import com.ecom.beans.Vendor;
import com.ecom.service.PhysicalProductService;
import com.ecom.service.VendorService;

@Controller
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vendorService;
	@Autowired
	PhysicalProductService physicalProductService;

	@RequestMapping(value = "/create-vendor")
	public String getVendors(Model model, @ModelAttribute(value = "vendor") Vendor vendor) {

		model.addAttribute("vendor", vendor);
		return "vendor-create";

	}

	@RequestMapping(value = "/saveVendor", method = RequestMethod.POST)
	public String saveVendor(Model model, @RequestParam("file") MultipartFile multipartfile, Vendor vendor)
			throws IOException {
		PhysicalProducts product = new PhysicalProducts();
		String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
		FileInputStream file = new FileInputStream("E://" + fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rows = sheet.getLastRowNum();
		for (int r = 1; r <= rows; r++) {
			XSSFRow row = sheet.getRow(r);
			int id = (int) row.getCell(0).getNumericCellValue();
			product.setProductId(id);
			product.setProductCompany(row.getCell(1).getStringCellValue());
			product.setProductPrice(row.getCell(2).getNumericCellValue());
			product.setProductCode(row.getCell(3).getStringCellValue());
			product.setProductDescription(row.getCell(4).getStringCellValue());
			product.setProductType(row.getCell(5).getStringCellValue());
			String path = row.getCell(6).getStringCellValue();
			product.setImage(Base64.getEncoder().encodeToString(path.getBytes()));
			product.setProductName(row.getCell(7).getStringCellValue());
			product.setIsactive('y');
			physicalProductService.addProduct(product);
		}

		vendor.setProductList(fileName);
		vendor.setIsActive('y');
		vendor.setCreated(LocalDate.now());
		vendor.setUpdated(LocalDate.now());

		vendorService.addVendor(vendor);

		return "redirect:/vendor/vendor-list";
	}

	@GetMapping(value = "/vendor-list")
	public String getAllVendors(Model model) {
		List<Vendor> list = vendorService.getAllVendors();
		model.addAttribute("vendorlist", list);

		return "vendor-list";
	}

	@GetMapping("/delete-vendor/{id}")
	public String deleteVendor(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		vendorService.deleteVendorById(id);

		List<Vendor> vendor = vendorService.getAllVendors();

		model.addAttribute("vendorList", vendor);

		return "redirect:/vendor/vendor-list";

	}

	@GetMapping("/edit-vendor/{id}")
	public String getVendorById(Model model, @PathVariable("id") int id) {
		System.out.println("..." + id);
		Vendor vendorObj = vendorService.getVendorById(id);
		model.addAttribute("vendorObj", vendorObj);
		return "edit-vendor";
	}

}
