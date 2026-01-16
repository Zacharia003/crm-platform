package com.crm.backend.auth.service;

import com.crm.backend.auth.dto.SignupRequest;

public interface SignupService {
	void signup(SignupRequest request);
}
