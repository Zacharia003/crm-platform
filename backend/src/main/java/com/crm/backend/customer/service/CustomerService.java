package com.crm.backend.customer.service;

import java.util.List;

import com.crm.backend.customer.Customer;

public interface CustomerService {

	Customer create(Customer customer);

	List<Customer> getAll();

	Customer update(Long id, Customer customer);

	void deactivate(Long id);

}