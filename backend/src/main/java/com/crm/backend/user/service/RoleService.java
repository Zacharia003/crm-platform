package com.crm.backend.user.service;

import java.util.List;

import com.crm.backend.user.Role;

public interface RoleService {
	Role create(Role role);

    List<Role> getAll();

}
