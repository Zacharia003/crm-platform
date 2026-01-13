package com.crm.backend.user;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.user.dto.ChangePasswordRequest;
import com.crm.backend.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.create(user);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		return userService.update(id, user);
	}

	@DeleteMapping("/{id}")
	public void deactivateUser(@PathVariable Long id) {
		userService.deactivate(id);
	}

	@PostMapping("/change-password")
	public void changePassword(@RequestBody ChangePasswordRequest request) {
		System.out.println("at users ChangePassword api");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String loggedInEmail = authentication.getName(); // JWT subject (email)
		System.out.println("loggedInEmail = "+loggedInEmail);

		userService.changePassword(loggedInEmail, request.getOldPassword(), request.getNewPassword());
	}

}
