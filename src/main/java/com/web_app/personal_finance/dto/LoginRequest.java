package com.web_app.personal_finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
	
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginRequest(@NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Password is required") @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters") String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	
	
}
