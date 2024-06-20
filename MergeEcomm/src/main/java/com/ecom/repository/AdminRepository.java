package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select al from Admin al where lower(al.employeeMail)=lower(?1) and al.employeePassword=?2")
	Admin findByUsernameIgnoreCaseAndPassword(String employeeMail, String employeePassword);

	@Query("select a1 from Admin a1 WHERE lower(a1.employeeMail)=lower(?1)")
	public Admin findByEmailId(String employeeMail);

	@Query(value = "SELECT employee_id, created, created_by, employee_designation, employee_mail, employee_mobile_number, employee_name, employee_password, is_active, updated, updated_by, image\r\n"
			+ "	FROM public.admin\r\n" + "	where employee_designation='Designer'", nativeQuery = true)
	public List<Admin> findByDesigner();

	@Query(value = "SELECT employee_id, created, created_by, employee_designation, employee_mail, employee_mobile_number, employee_name, employee_password, is_active, updated, updated_by, image\r\n"
			+ "	FROM public.admin\r\n" + "	where employee_designation!='Designer'", nativeQuery = true)
	public List<Admin> findAllRoles();

}
