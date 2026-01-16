package com.crm.backend.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.exception.BadRequestException;
import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.product.Product;
import com.crm.backend.product.ProductRepository;
import com.crm.backend.security.CurrentUserContext;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CurrentUserContext currentUserContext;

	public ProductServiceImpl(ProductRepository productRepository, CurrentUserContext currentUserContext) {
		this.productRepository = productRepository;
		this.currentUserContext = currentUserContext;
	}

	@Override
	public Product create(Product product) {

		Long companyId = currentUserContext.getCompanyId();

		// SKU uniqueness per company
		productRepository.findBySkuAndCompanyId(product.getSku(), companyId).ifPresent(p -> {
			throw new BadRequestException("SKU already exists for this company");
		});

		// Attach company from context
		Company company = currentUserContext.getUser().getCompany();
		product.setCompany(company);

		if (product.getActive() == null) {
			product.setActive(true);
		}

		return productRepository.save(product);
	}

	@Override
	public Product update(Long id, Product updatedProduct) {

		Long companyId = currentUserContext.getCompanyId();

		Product product = productRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		product.setName(updatedProduct.getName());
		product.setDescription(updatedProduct.getDescription());
		product.setUnitPrice(updatedProduct.getUnitPrice());
		product.setAttributes(updatedProduct.getAttributes());

		// Preserve unit if not provided
		if (updatedProduct.getUnit() != null) {
			product.setUnit(updatedProduct.getUnit());
		}

		// Optional HSN
		product.setHsnCode(updatedProduct.getHsnCode());

		return productRepository.save(product);
	}

	@Override
	public void deactivate(Long id) {

		Long companyId = currentUserContext.getCompanyId();

		Product product = productRepository.findByIdAndCompanyId(id, companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		product.setActive(false);
		productRepository.save(product);
	}

	@Override
	public List<Product> getAllActive() {

		Long companyId = currentUserContext.getCompanyId();

		return productRepository.findByCompanyIdAndActiveTrue(companyId);
	}
}
