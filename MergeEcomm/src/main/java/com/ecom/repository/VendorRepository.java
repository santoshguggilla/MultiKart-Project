package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.beans.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

}
