package com.crm.backend.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.customer.Customer;
import com.crm.backend.customer.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CompanyRepository companyRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, CompanyRepository companyRepository) {
		this.customerRepository = customerRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public Customer create(Customer customer) {
		Company company = companyRepository.findById(customer.getCompany().getId())
				.orElseThrow(() -> new RuntimeException("Company not found"));

		customer.setCompany(company);

		// ✅ Ensure business default
		if (customer.getActive() == null) {
			customer.setActive(true);
		}

		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Long id, Customer updatedCustomer) {

		Customer existing = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		existing.setName(updatedCustomer.getName());
		existing.setEmail(updatedCustomer.getEmail());
		existing.setPhone(updatedCustomer.getPhone());
		existing.setAddress(updatedCustomer.getAddress());
		existing.setActive(updatedCustomer.getActive());

		return customerRepository.save(existing);
	}

	@Override
	public void deactivate(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		customer.setActive(false);
		customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}
}
