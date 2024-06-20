package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.beans.CustomerPaymentDetails;

@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPaymentDetails, Integer> {

}
