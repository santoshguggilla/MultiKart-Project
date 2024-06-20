package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.beans.PhysicalProducts;
@Repository
public interface PhysicalProductRepository extends JpaRepository<PhysicalProducts, Integer> {
	
	@Query(value="SELECT product_id, created, createdby, image, isactive, product_code, product_company, product_description, product_name, product_price, product_type, updated, updatedby\r\n"
			+ "	FROM public.physical_products\r\n"
			+ "	where created=current_date",nativeQuery = true)
	List<PhysicalProducts> findLatestProducts();

}
