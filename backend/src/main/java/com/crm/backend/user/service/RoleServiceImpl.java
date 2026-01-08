package com.crm.backend.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.backend.user.Role;
import com.crm.backend.user.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role create(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAll() {
		return roleRepository.findAll();
	}
}
