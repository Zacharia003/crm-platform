package com.crm.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSignup {
	private String name;
	private String email;
	private String mobile;
	private String password;
}
