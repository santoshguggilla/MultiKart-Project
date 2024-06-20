package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.beans.CustomerCart;

public interface CustomerCartERepository extends JpaRepository<CustomerCart, Integer>{

}
