package com.enterprise.auth_service.dto;

import com.enterprise.auth_service.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
	

	@NotBlank(message="Username is required")
	@Size(min=3,max=20)
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@NotBlank(message="Email is required")
	@Email
	private String email;
	
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
	private String phone;
    
    @NotNull(message="Role is required")
	private Role role;


}
