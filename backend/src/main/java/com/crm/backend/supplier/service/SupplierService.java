package com.crm.backend.supplier.service;

import java.util.List;

import com.crm.backend.supplier.Supplier;

public interface SupplierService {

	Supplier create(Supplier supplier);

	Supplier update(Long id, Supplier supplier);

	void deactivate(Long id);

	List<Supplier> getAllActive();
}