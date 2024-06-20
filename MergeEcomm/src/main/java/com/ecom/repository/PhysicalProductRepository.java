package com.ecom.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.beans.PhysicalProducts;
@Repository
public interface PhysicalProductRepository extends JpaRepository<PhysicalProducts, Integer> {
	
	@Query("Select p from PhysicalProducts p where p.created=current_date")
	List<PhysicalProducts> findLatestProducts();
	@Query("select al from PhysicalProducts al where lower(al.productModelNumber)=lower(?1)")
	List<PhysicalProducts> getPhysicalProductsByModelNumber(String model);
	@Query("select al from PhysicalProducts al where lower(al.isactive)='y' or lower(al.isactive)='Y'")
	List<PhysicalProducts> getActivePhysicalProducts();
}
