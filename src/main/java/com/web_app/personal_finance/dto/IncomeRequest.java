package com.web_app.personal_finance.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class IncomeRequest {
		
	private Long id;
	
	@Column(nullable = false)
	private Double income;
	
	@Column(nullable=false)
	@Size(min=3,max=3)
	private String currency;
	
	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = true)
	@Size(min = 0, max = 250)
	private String Description;

	@Column(name = "income_source_id", nullable = false)
	private Long income_source;

	public IncomeRequest(Long id,Double income, LocalDate date,@Size(min = 0, max = 250) String description,
			Long income_source) {
		super();
		this.id=id;
		this.income = income;
		this.date = date;
		Description = description;
		this.income_source = income_source;
	}
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Long getIncome_source() {
		return income_source;
	}

	public void setIncome_source(Long income_source) {
		this.income_source = income_source;
	}
	
}
