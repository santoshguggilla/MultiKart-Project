package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.beans.BilingDetails;

public interface BillingRepository extends JpaRepository<BilingDetails, Integer>{

}
