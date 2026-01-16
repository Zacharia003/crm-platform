package com.crm.backend.supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByCompanyIdAndActiveTrue(Long companyId);

    Optional<Supplier> findByIdAndCompanyId(Long id, Long companyId);
}

