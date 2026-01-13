package com.crm.backend.supplier.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.supplier.Supplier;
import com.crm.backend.supplier.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

	private final SupplierRepository supplierRepository;
	private final CompanyRepository companyRepository;

	public SupplierServiceImpl(SupplierRepository supplierRepository, CompanyRepository companyRepository) {
		this.supplierRepository = supplierRepository;
		this.companyRepository = companyRepository;
	}

	//supplier create service
	@Override
	public Supplier create(Supplier supplier) {

		Company company = companyRepository.findById(supplier.getCompany().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		supplier.setCompany(company);

		if (supplier.getActive() == null) {
			supplier.setActive(true);
		}

		return supplierRepository.save(supplier);
	}

	//supplier update service
	@Override
	public Supplier update(Long id, Supplier updatedSupplier) {

		Supplier existing = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

		existing.setName(updatedSupplier.getName());
		existing.setEmail(updatedSupplier.getEmail());
		existing.setPhone(updatedSupplier.getPhone());
		existing.setAddress(updatedSupplier.getAddress());

		return supplierRepository.save(existing);
	}

	//supplier deactivate service
	@Override
	public void deactivate(Long id) {

		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

		supplier.setActive(false);
		supplierRepository.save(supplier);
	}

	//supplier fetch data service
	@Override
	public List<Supplier> getAllActive() {
		return supplierRepository.findByActiveTrue();
	}
}
