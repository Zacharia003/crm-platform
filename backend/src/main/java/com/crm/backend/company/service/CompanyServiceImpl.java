package com.crm.backend.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.user.Role;
import com.crm.backend.user.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final RoleRepository roleRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository, RoleRepository roleRepository) {
		this.companyRepository = companyRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public Company create(Company company) {

		Company savedCompany = companyRepository.save(company);

		List<String> defaultRoles = List.of("ADMIN", "SALES", "STORE", "ACCOUNTS");

		for (String roleName : defaultRoles) {

			boolean exists = roleRepository.findByNameAndCompanyId(roleName, 
					savedCompany.getId()).isPresent();

			if (!exists) {
				Role role = new Role();
				role.setName(roleName);
				role.setCompany(savedCompany);
				roleRepository.save(role);
			}
		}

		return savedCompany;
	}

//	@Override
//	public List<Company> findAll() {
//		return companyRepository.findAll();
//	}
}
