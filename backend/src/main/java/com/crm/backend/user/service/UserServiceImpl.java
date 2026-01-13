package com.crm.backend.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.backend.company.Company;
import com.crm.backend.company.CompanyRepository;
import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.exception.UnauthorizedException;
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
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Role role = roleRepository.findById(user.getRole().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		user.setCompany(company);
		user.setRole(role);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getActive() == null) {
			user.setActive(true);
		}

		return userRepository.save(user);
	}

	@Override
	public User update(Long id, User updatedUser) {

		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setCountryCode(updatedUser.getCountryCode());
		existingUser.setMobileNumber(updatedUser.getMobileNumber());
		existingUser.setActive(updatedUser.getActive());

		if (updatedUser.getRole() != null) {
			Role role = roleRepository.findById(updatedUser.getRole().getId())
					.orElseThrow(() -> new RuntimeException("Role not found"));
			existingUser.setRole(role);
		}

		return userRepository.save(existingUser);
	}

	@Override
	public void deactivate(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	public void changePassword(String email, String oldPassword, String newPassword) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new UnauthorizedException("Old password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
}
