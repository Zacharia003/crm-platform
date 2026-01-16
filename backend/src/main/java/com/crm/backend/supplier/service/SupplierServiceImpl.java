package com.crm.backend.supplier.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.security.CurrentUserContext;
import com.crm.backend.supplier.Supplier;
import com.crm.backend.supplier.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

	private final SupplierRepository supplierRepository;
	private final CurrentUserContext currentUserContext;

	public SupplierServiceImpl(SupplierRepository supplierRepository, CurrentUserContext currentUserContext) {
		this.supplierRepository = supplierRepository;
		this.currentUserContext = currentUserContext;
	}

	@Override
	public Supplier create(Supplier supplier) {

		Company company = currentUserContext.getUser().getCompany();
		supplier.setCompany(company);

		if (supplier.getActive() == null) {
			supplier.setActive(true);
		}

		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier update(Long id, Supplier updatedSupplier) {

		Long companyId = currentUserContext.getCompanyId();

		Supplier existing = supplierRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

		existing.setName(updatedSupplier.getName());
		existing.setEmail(updatedSupplier.getEmail());
		existing.setPhone(updatedSupplier.getPhone());
		existing.setAddress(updatedSupplier.getAddress());

		return supplierRepository.save(existing);
	}

	@Override
	public void deactivate(Long id) {

		Long companyId = currentUserContext.getCompanyId();

		Supplier supplier = supplierRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

		supplier.setActive(false);
		supplierRepository.save(supplier);
	}

	@Override
	public List<Supplier> getAllActive() {

		Long companyId = currentUserContext.getCompanyId();
		return supplierRepository.findByCompanyIdAndActiveTrue(companyId);
	}
}
