package com.crm.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.crm.backend.exception.UnauthorizedException;
import com.crm.backend.security.JwtUtil;
import com.crm.backend.user.User;
import com.crm.backend.user.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request) {

		String identifier = request.getIdentifier();
		User user;

		if (identifier.contains("@")) {
			// Email login
			user = userRepository.findByEmail(identifier)
					.orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
		} else {
			// Mobile login: +919876543210
			if (!identifier.startsWith("+") || identifier.length() < 8) {
				throw new UnauthorizedException("Invalid credentials");
			}

			String countryCode = identifier.substring(0, 3); // +91
			String mobile = identifier.substring(3);

			user = userRepository.findByCountryCodeAndMobileNumber(countryCode, mobile)
					.orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new UnauthorizedException("Invalid credentials");
		}

		return jwtUtil.generateToken(user.getEmail()); // subject = email (stable)
	}

}
