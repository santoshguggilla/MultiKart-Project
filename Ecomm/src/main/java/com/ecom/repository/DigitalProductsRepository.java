package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.beans.DigitalProducts;
@Repository
public interface DigitalProductsRepository extends JpaRepository<DigitalProducts, Integer> {
//@Query("SELECT p FROM  DigitalProducts p WHERE p.productName LIKE %?1%"
//		+"OR p.ProductCode LIKE %?1%" +"OR p.productId LIKE %?1%")

@Query(value="select * from digital_products p where p.product_name like %:keyword%", nativeQuery = true)

//List<AdminDetails> findByKeyword(@Param("keyword") String keyword);
	 List<DigitalProducts> findByKeyword(@Param("keyword") String keyword);
}
