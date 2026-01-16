package com.crm.backend.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.auth.dto.SignupRequest;
import com.crm.backend.auth.service.SignupService;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

	private final SignupService signupService;

	public SignupController(SignupService signupService) {
		this.signupService = signupService;
	}

	@PostMapping
	public String signup(@RequestBody SignupRequest request) {
		signupService.signup(request);
		return "Company and admin user created successfully";
	}
}
