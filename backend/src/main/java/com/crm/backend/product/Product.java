package com.crm.backend.product;

import com.crm.backend.company.Company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products", uniqueConstraints = { @UniqueConstraint(columnNames = { "sku", "company_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String sku;

	private String description;

	private Double unitPrice;

	@Column(nullable = false)
	private String unit;

	private String hsnCode;

	@Column(columnDefinition = "json")
	private String attributes; // JSON string

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Column(nullable = false)
	private Boolean active = true;
}
