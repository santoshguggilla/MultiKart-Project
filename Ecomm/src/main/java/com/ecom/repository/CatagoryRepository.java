package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.beans.Catagory;
@Repository
public interface CatagoryRepository extends JpaRepository<Catagory, Integer> {
 
}
