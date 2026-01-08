package com.crm.backend.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.user.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping
	public Role createRole(@RequestBody Role role) {
		return roleService.create(role);
	}

	@GetMapping
	public List<Role> getAllRoles() {
		return roleService.getAll();
	}
}
