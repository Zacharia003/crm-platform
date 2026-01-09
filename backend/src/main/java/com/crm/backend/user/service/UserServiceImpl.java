package com.crm.backend.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.user.Role;
import com.crm.backend.user.RoleRepository;
import com.crm.backend.user.User;
import com.crm.backend.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User create(User user) {
		Company company = companyRepository.findById(user.getCompany().getId())
				.orElseThrow(() -> new RuntimeException("Company not found"));

		Role role = roleRepository.findById(user.getRole().getId())
				.orElseThrow(() -> new RuntimeException("Role not found"));

		user.setCompany(company);
		user.setRole(role);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
}
