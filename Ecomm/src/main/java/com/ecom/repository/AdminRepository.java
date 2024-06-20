package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.beans.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select al from Admin al where lower(al.employeeMail)=lower(?1) and al.employeePassword=?2")
	Admin findByUsernameIgnoreCaseAndPassword(String employeeMail,String employeePassword);

	@Query("select a1 from Admin a1 WHERE lower(a1.employeeMail)=lower(?1)")
	public Admin findByEmailId(String employeeMail);
	
}
