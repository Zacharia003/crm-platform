package com.crm.backend.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.backend.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
