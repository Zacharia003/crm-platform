package com.crm.backend.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final RoleRepository roleRepository;

	public UserController(UserRepository userRepository, CompanyRepository companyRepository,
			RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.roleRepository = roleRepository;
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		// TEMPORARY: assume company & role IDs are valid
		Company company = companyRepository.findById(user.getCompany().getId())
				.orElseThrow(() -> new RuntimeException("Company not found"));

		Role role = roleRepository.findById(user.getRole().getId())
				.orElseThrow(() -> new RuntimeException("Role not found"));

		user.setCompany(company);
		user.setRole(role);

		return userRepository.save(user);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
