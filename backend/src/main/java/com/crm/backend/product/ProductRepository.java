package com.crm.backend.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCompanyIdAndActiveTrue(Long companyId);

	Optional<Product> findByIdAndCompanyId(Long id, Long companyId);
	Optional<Product> findBySkuAndCompanyId(String sku, Long companyId);


}
