package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.beans.CustomerCart;
@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, Integer>{
 @Query("select al from CustomerCart al where lower(al.isActive)='y' or lower(al.isActive)='Y'")
 List<CustomerCart> getActiveList();
 
 @Query("select sum(totalprice)  from CustomerCart al where al.customerId=?1")
 double getcarttotal(int customerId);
 @Query("select al from CustomerCart al where (lower(al.isActive)='y' or lower(al.isActive)='Y')and al.customerId=?1")
 List<CustomerCart> getCartActiveList(int id);
}
