package com.crm.backend.product.service;

import java.util.List;
import com.crm.backend.product.Product;

public interface ProductService {

	Product create(Product product);

	Product update(Long id, Product product);

	void deactivate(Long id);

	List<Product> getAllActive();
}
