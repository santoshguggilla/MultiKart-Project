package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.beans.CustomerCart;
@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, Integer>{

}
