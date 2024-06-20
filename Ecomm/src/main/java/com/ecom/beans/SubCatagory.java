package com.ecom.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SubCatagory")
public class SubCatagory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int subcatagoryId;
	String SubcatagoryName;
	String CatagoryName;
	
 	public int getSubcatagoryId() {
		return subcatagoryId;
	}
	public void setSubcatagoryId(int subcatagoryId) {
		this.subcatagoryId = subcatagoryId;
	}
	public String getSubcatagoryName() {
		return SubcatagoryName;
	}
	public void setSubcatagoryName(String subcatagoryName) {
		SubcatagoryName = subcatagoryName;
	}
	public String getCatagoryName() {
		return CatagoryName;
	}
	public void setCatagoryName(String catagoryName) {
		CatagoryName = catagoryName;
	}
	public int getCatagoryId() {
		return catagoryId;
	}
	public void setCatagoryId(int catagoryId) {
		this.catagoryId = catagoryId;
	}
	int catagoryId;
}
