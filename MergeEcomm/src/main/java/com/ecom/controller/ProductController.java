package com.ecom.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.mail.smime.handlers.pkcs7_signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.beans.PhysicalCategory;
import com.ecom.beans.PhysicalProducts;
import com.ecom.beans.PhysicalSubCategory;
import com.ecom.beans.ProductImage;
import com.ecom.repository.PhysicalProductRepository;
import com.ecom.service.ImageService;
import com.ecom.service.PhysicalCategoryService;
import com.ecom.service.PhysicalProductService;
import com.ecom.service.PhysicalSubCategoryService;
import com.google.zxing.WriterException;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	PhysicalProductRepository physicalproductrepository;
	@Autowired
	PhysicalProductService physicalproductservice;
	@Autowired
	ImageService imageService;
	@Autowired
	PhysicalCategoryService PhysicalCategoryServices;
	@Autowired
	PhysicalSubCategoryService PhysicalSubCategoryServices;
	
	@RequestMapping("/productdetails")
	public String ProductDetailsCreation(Model model, PhysicalProducts productobject) {
		List<PhysicalCategory> categoryList = PhysicalCategoryServices.getAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productobject", productobject);

		return "add-product";

	}

	@RequestMapping("/productsphotos")
	public String photomethod(Model model, @ModelAttribute(value = "productobject") PhysicalProducts productobject,
			final HttpServletRequest request, @RequestParam("quatity") int quatity, @RequestParam("Size") String Size)
			throws WriterException, IOException {

		List<PhysicalCategory> categoryList = PhysicalCategoryServices.getAllCategory();
		model.addAttribute("categoryList", categoryList);
		String getQry = request.getParameter("getQry");
		String catagoryId = request.getParameter("catagoryId");

		if (getQry != null && getQry.equals("getSubsCatagory") && catagoryId != null && catagoryId != "") {

			List<PhysicalSubCategory> subCatagoryList = PhysicalSubCategoryServices
					.findPhysicalSubCategoryByCategory(Integer.valueOf(catagoryId));
			model.addAttribute("subCatagoryList", subCatagoryList);

			return "add-product";

		}

		List<PhysicalSubCategory> subCatagoryList = PhysicalSubCategoryServices
				.findPhysicalSubCategoryByCategory(Integer.valueOf(productobject.getProductCategory()));
		productobject.setIsactive('Y');
		byte[] array1 = new byte[7];
		new Random().nextBytes(array1);
		String generatedString = new String(array1);
		productobject.setProductCode(generatedString);
		productobject.setProductSize(Size);
		PhysicalProducts object1 = physicalproductservice.addProduct(productobject);
		for (int i = 0; i < quatity-1; i++) {

			PhysicalProducts productobject1 = new PhysicalProducts();
			productobject1.setProductModelNumber(object1.getProductModelNumber());
			productobject1.setProductName(object1.getProductName());
			productobject1.setProductCategory(object1.getProductCategory());
			productobject1.setProductSubCategory(object1.getProductSubCategory());
			productobject1.setProductCompany(object1.getProductCompany());
			productobject1.setProductDescription(object1.getProductDescription());
			productobject1.setProductDiscountPrice(object1.getProductDiscountPrice());
			productobject1.setProductMRPPrice(object1.getProductMRPPrice());
			productobject1.setProductSize(Size);
			productobject1.setProductId((int) Math.random() * 90);
			productobject1.setIsactive('N');
			byte[] array = new byte[7];
			new Random().nextBytes(array);
			String generatedStrings = new String(array, Charset.forName("UTF-8"));
			productobject1.setProductCode(generatedStrings);
			PhysicalProducts object = physicalproductservice.addProduct(productobject1);
			model.addAttribute("productobject", object);

		}

		model.addAttribute("subCatagoryList", subCatagoryList);
		model.addAttribute("physicalproductid", productobject.getProductId());
		System.out.println(productobject.getProductId());
		return "upload-view";
	}

	@RequestMapping(value = "/productimage/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String fileUpload(Model model, @RequestParam("files") MultipartFile file, HttpSession session,
			@PathVariable(value = "id") int productid, PhysicalProducts productobject)
			throws IOException, WriterException {
		PhysicalProducts object = physicalproductservice.getProductById(productid);
		List<PhysicalProducts> physicalproductmodel = physicalproductrepository.getPhysicalProductsByModelNumber(object.getProductModelNumber());
		for (PhysicalProducts product : physicalproductmodel) {
			try {
				product.setProductImage(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();

			}

			product.setCreated(LocalDate.now());
			product.setUpdated(LocalDate.now());

			PhysicalProducts physicalproduct = physicalproductrepository.save(product);
			model.addAttribute("physicalproduct", physicalproduct);
		}
		return "add-mutliple-physical-product-images";

	}

	@RequestMapping("uploadphysicalproductimages/{id}")

	public String uploadMultipleImages(Model model, @PathVariable(value = "id") int id,
			@RequestParam("files") MultipartFile[] file, ProductImage image, HttpServletResponse response)
			throws IOException, WriterException {
		PhysicalProducts physicalProducts = physicalproductservice.getProductById(id);
		List<PhysicalProducts> physicalproductmodel = physicalproductrepository.getPhysicalProductsByModelNumber(physicalProducts.getProductModelNumber());
		for (PhysicalProducts product : physicalproductmodel) {
			image.setProductId(product.getProductId());

			for (MultipartFile f : file) {

				int Random = (int) (Math.random() * 90);
				image.setImageId(Random);
				try {
					image.setImage(Base64.getEncoder().encodeToString(f.getBytes()));
				} catch (Exception e) {
					e.printStackTrace();
				}

				imageService.addImage(image);
			}
		}
		return "redirect:/product/productlist";
	}

	@RequestMapping("/productlist")
	public String method(Model model) {
		List<PhysicalProducts> object=physicalproductrepository.getActivePhysicalProducts();
		
		model.addAttribute("productlist", object);
		return "product-list";
	}

	@RequestMapping("/deletePhysicalProduct/{id}")
	public String deletePhysicalProducts(Model model, @PathVariable("id") int id) {

		System.out.println("ssss");
		physicalproductservice.deleteBydataId(id);

		return "redirect:/product/productlist";

	}

	@RequestMapping("/editPhysicalproducts/{id}")
	public String EditPhysicalByProductId(Model model, @PathVariable("id") int id) {
		PhysicalProducts products = physicalproductservice.getProductById(id);
		model.addAttribute("products", products);
		return "edit-physical-product";
	}

	@RequestMapping("/editimage")
	public String editProductImage(Model model, PhysicalProducts physicalProducts) {
		physicalProducts.setIsactive('Y');

		PhysicalProducts product = physicalproductservice.addProduct(physicalProducts);
		model.addAttribute("product", product);
		return "edit-physical-product-image";

	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateimage(Model model, @RequestParam("files") MultipartFile file, HttpSession session,
			@PathVariable(value = "id") int productid, PhysicalProducts productobject) {
		PhysicalProducts object = physicalproductservice.getProductById(productid);
		System.out.println(object);
		if (file.getOriginalFilename() == "") {
			object.setProductImage(object.getProductImage());
		} else {
			try {
				object.setProductImage(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		object.setCreated(LocalDate.now());
		object.setUpdated(LocalDate.now());
		PhysicalProducts physicalproduct = physicalproductrepository.save(object);
		model.addAttribute("physicalproduct", physicalproduct);
		ArrayList<ProductImage> productimage = imageService.getPhysicalProductImages(productid);
		model.addAttribute("productimage", productimage);

		return "edit-multiple-images";

	}

	@RequestMapping("/addmultipleproducts")
	public String AddMultipleProducts(Model model, PhysicalProducts product) throws IOException {
		FileInputStream file = new FileInputStream("E:\\Book1.xlsx");
		try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			int rows = sheet.getLastRowNum();
			for (int r = 1; r <= rows; r++) {
				XSSFRow row = sheet.getRow(r);
				int id = (int) row.getCell(0).getNumericCellValue();
				// product.setProductId(id);
				product.setProductCompany(row.getCell(1).getStringCellValue());
				product.setProductMRPPrice(row.getCell(2).getNumericCellValue());
				product.setProductCode(row.getCell(3).getStringCellValue());
				product.setProductDescription(row.getCell(4).getStringCellValue());
				product.setProductImage(row.getCell(6).getStringCellValue());
				product.setProductName(row.getCell(7).getStringCellValue());
				product.setIsactive('y');
				physicalproductservice.addProduct(product);
			}
		}
		return "redirect:/product/productlist";
	}

	@RequestMapping("/addmultipleimages/{id}")
	public String addMultipleImages(Model model, @PathVariable(value = "id") int id) {
		PhysicalProducts physicalProducts = physicalproductservice.getProductById(id);
		model.addAttribute("physicalProducts", physicalProducts);

		return "add-mutliple-physical-product-images";
	}

	@RequestMapping("/editmultipleimages/{id}")
	public String editMultipleImage(Model model, @RequestParam("files") MultipartFile file, HttpSession session,
			@PathVariable(value = "id") int productid, PhysicalProducts productobject) {
		PhysicalProducts object = physicalproductservice.getProductById(productid);

		try {
			object.setProductImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();

		}

		object.setCreated(LocalDate.now());
		object.setUpdated(LocalDate.now());
		PhysicalProducts physicalproduct = physicalproductrepository.save(object);
		model.addAttribute("physicalproduct", physicalproduct);
		ArrayList<ProductImage> productimage = imageService.getPhysicalProductImages(productid);
		model.addAttribute("productimage", productimage);
		return "edit-multiple-images";

	}

	@RequestMapping("/physicalproductdetails/{id}")
	public String getdetails(@PathVariable(value = "id") int productid, Model model) {
		PhysicalProducts product = physicalproductservice.getProductById(productid);
		ArrayList<ProductImage> productImages = imageService.getPhysicalProductImages(productid);
		model.addAttribute("productImages", productImages);
		model.addAttribute("product", product);
		System.out.println(product.getProductId());
		return "product-detail";
	}

	@RequestMapping("/productdetails/{id}")
	public String getProductdetailsByProductId(Model model, @PathVariable("id") int id, HttpSession session) {
		PhysicalProducts productlist = physicalproductservice.getProductById(id);

		model.addAttribute("productlist", productlist);

		System.err.println(productlist.getProductImage());

		return "product-detail";
	}

}
