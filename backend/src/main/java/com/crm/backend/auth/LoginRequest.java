package com.crm.backend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String identifier; // email OR mobile
	private String password;
}
