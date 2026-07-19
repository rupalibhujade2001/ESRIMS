package com.enterprise.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import com.enterprise.auth_service.entity.Role;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String userName;

	@Email
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String passowrd;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private String farmerName;
	
	@Column(nullable = false)
	private String phone;

	public User() {

	}

	public User(Long Id, String UserName, String email, String password, String farmerName) {
		this.id = Id;
		this.userName = UserName;
		this.email = email;
		this.passowrd = password;
		this.farmerName = farmerName;
	}

}
