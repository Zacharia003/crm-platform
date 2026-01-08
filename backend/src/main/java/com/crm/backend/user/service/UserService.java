package com.crm.backend.user.service;

import java.util.List;

import com.crm.backend.user.User;

public interface UserService {

    User create(User user);

    List<User> getAll();
}
