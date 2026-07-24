package com.enterprise.auth_service.dto;

import com.enterprise.auth_service.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	@NotBlank(message="Username is required")
	@Size(min=3,max=20)
	private String username;
	
	@NotBlank(message="Email is required")
	@Email
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min=8)
	private String password;
	
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
	private String phone;
    
    @NotNull(message="Role is required")
	private Role role;
    
    @NotBlank(message="Password is required")
	@Size(min=8)
    private String farmerName;

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

}
