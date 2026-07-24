package com.enterprise.auth_service.dto;

import com.enterprise.auth_service.entity.Role;

public class LoginResponse {
	
	  public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public Role getRole() {
		return role;
	}
	  public void setRole(Role role) {
			this.role = role;
		}
	
	    private String token;
	    private String type;
	 //   private String username;
		private Role role;
	    private String email;
	    private String message;
	    public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		



}
