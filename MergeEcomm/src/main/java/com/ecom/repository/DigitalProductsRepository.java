package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.beans.DigitalProducts;

@Repository
public interface DigitalProductsRepository extends JpaRepository<DigitalProducts, Integer> {

	@Query(value = "select * from digital_products p where p.product_name like %:keyword%", nativeQuery = true)
	List<DigitalProducts> findByKeyword(@Param("keyword") String keyword);
}
