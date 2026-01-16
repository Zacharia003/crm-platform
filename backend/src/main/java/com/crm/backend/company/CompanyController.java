package com.crm.backend.company;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.backend.company.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private final CompanyService companyservice;

	public CompanyController(CompanyService companyservice) {
		this.companyservice = companyservice;
	}

	@PostMapping
	public Company createCompany(@RequestBody Company company) {
		return companyservice.create(company);
	}

//	@GetMapping
//	public List<Company> getAllCompanies() {
//		return CompanyService.findAll();
//	}
}
