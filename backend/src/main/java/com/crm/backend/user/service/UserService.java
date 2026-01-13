package com.crm.backend.user.service;

import java.util.List;

import com.crm.backend.user.User;

public interface UserService {

	User create(User user);

	User update(Long id, User user);

	void deactivate(Long id);

	List<User> getAll();

	void changePassword(String email, String oldPassword, String newPassword);

}
