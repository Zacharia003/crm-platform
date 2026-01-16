package com.crm.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	private CompanySignup company;
	private AdminSignup admin;
}
