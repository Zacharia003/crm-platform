package com.crm.backend.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerService.create(customer);
	}

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerService.getAll();
	}

	@PutMapping("/{id}")
	public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		return customerService.update(id, customer);
	}

	@DeleteMapping("/{id}")
	public void deactivateCustomer(@PathVariable Long id) {
		customerService.deactivate(id);
	}

}
