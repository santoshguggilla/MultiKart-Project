package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.beans.CustomerAddress;
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Integer>{

}
