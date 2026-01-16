package com.crm.backend.product;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public Product create(@RequestBody Product product) {
		return productService.create(product);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody Product product) {
		return productService.update(id, product);
	}

	@DeleteMapping("/{id}")
	public void deactivate(@PathVariable Long id) {
		productService.deactivate(id);
	}

	@GetMapping
	public List<Product> getAll() {
		return productService.getAllActive();
	}
}
