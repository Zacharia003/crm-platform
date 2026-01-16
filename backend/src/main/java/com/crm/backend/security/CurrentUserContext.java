package com.crm.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.crm.backend.exception.ResourceNotFoundException;
import com.crm.backend.user.User;
import com.crm.backend.user.UserRepository;

@Component
public class CurrentUserContext {

	private final UserRepository userRepository;

	public CurrentUserContext(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	public Long getCompanyId() {
		return getUser().getCompany().getId();
	}
}
