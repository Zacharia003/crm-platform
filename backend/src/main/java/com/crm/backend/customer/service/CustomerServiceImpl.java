package com.crm.backend.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.customer.Customer;
import com.crm.backend.customer.CustomerRepository;
import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.security.CurrentUserContext;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CurrentUserContext currentUserContext;

	public CustomerServiceImpl(CustomerRepository customerRepository, CurrentUserContext currentUserContext) {
		this.customerRepository = customerRepository;
		this.currentUserContext = currentUserContext;
	}

	@Override
	public Customer create(Customer customer) {

		Company company = currentUserContext.getUser().getCompany();
		customer.setCompany(company);

		if (customer.getActive() == null) {
			customer.setActive(true);
		}

		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Long id, Customer updatedCustomer) {

		Long companyId = currentUserContext.getCompanyId();

		Customer customer = customerRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		customer.setName(updatedCustomer.getName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setPhone(updatedCustomer.getPhone());
		customer.setAddress(updatedCustomer.getAddress());

		return customerRepository.save(customer);
	}

	@Override
	public void deactivate(Long id) {

		Long companyId = currentUserContext.getCompanyId();

		Customer customer = customerRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		customer.setActive(false);
		customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllActive() {

		Long companyId = currentUserContext.getCompanyId();
		return customerRepository.findByCompanyIdAndActiveTrue(companyId);
	}
}
