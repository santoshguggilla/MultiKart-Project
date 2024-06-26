package com.ecom.service;

import java.util.List;

import com.ecom.beans.Vendor;

public interface VendorService {

	Vendor addVendor(Vendor vendor);

	List<Vendor> getAllVendors();

	void deleteVendorById(int id);

	Vendor getVendorById(int id);

}
