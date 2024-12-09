package com.web_app.personal_finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpRequest {
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
	private String password;
	
	@NotBlank(message = "Confirm Password is required")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 50 characters")
	private String confirm_password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public SignUpRequest(
			@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Password is required") 
			@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters") String password,
			@NotBlank(message = "Confirm Password is required") 
			@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters") String confirm_password
	)	
	{
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirm_password=confirm_password;
	}
	
	
	
}
