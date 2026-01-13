package com.crm.backend.supplier;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.supplier.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

	private final SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@PostMapping
	public Supplier create(@RequestBody Supplier supplier) {
		return supplierService.create(supplier);
	}

	@PutMapping("/{id}")
	public Supplier update(@PathVariable Long id, @RequestBody Supplier supplier) {
		return supplierService.update(id, supplier);
	}

	@DeleteMapping("/{id}")
	public void deactivate(@PathVariable Long id) {
		supplierService.deactivate(id);
	}

	@GetMapping
	public List<Supplier> getAll() {
		return supplierService.getAllActive();
	}
}
