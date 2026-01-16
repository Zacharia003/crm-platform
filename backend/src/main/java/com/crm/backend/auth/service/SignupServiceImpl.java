package com.crm.backend.auth.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.backend.auth.dto.SignupRequest;
import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.exception.BadRequestException;
import com.crm.backend.user.Role;
import com.crm.backend.user.RoleRepository;
import com.crm.backend.user.User;
import com.crm.backend.user.UserRepository;

@Service
public class SignupServiceImpl implements SignupService {

	private final CompanyRepository companyRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SignupServiceImpl(CompanyRepository companyRepository, RoleRepository roleRepository,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.companyRepository = companyRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void signup(SignupRequest request) {

		if (userRepository.existsByEmail(request.getAdmin().getEmail())) {
			throw new BadRequestException("Admin email already exists");
		}

		// 1. Create Company
		Company company = new Company();
		company.setName(request.getCompany().getName());
		company.setEmail(request.getCompany().getEmail());
		company.setPhone(request.getCompany().getPhone());

		Company savedCompany = companyRepository.save(company);

		// 2. Create Roles (idempotent)
		List<String> defaultRoles = List.of("ADMIN", "SALES", "STORE", "ACCOUNTS");

		for (String roleName : defaultRoles) {
			if (roleRepository.findByNameAndCompanyId(roleName, savedCompany.getId()).isEmpty()) {
				Role role = new Role();
				role.setName(roleName);
				role.setCompany(savedCompany);
				roleRepository.save(role);
			}
		}

		// 3. Fetch ADMIN role explicitly
		Role adminRole = roleRepository.findByNameAndCompanyId("ADMIN", savedCompany.getId())
				.orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

		// 4. Create Admin User
		User admin = new User();
		admin.setName(request.getAdmin().getName());
		admin.setEmail(request.getAdmin().getEmail());
		admin.setCountryCode("+91"); // or from request if applicable
		admin.setMobileNumber(request.getAdmin().getMobile());
		admin.setPassword(passwordEncoder.encode(request.getAdmin().getPassword()));
		admin.setCompany(savedCompany);
		admin.setRole(adminRole);
		admin.setActive(true);

		userRepository.save(admin);
	}
}
