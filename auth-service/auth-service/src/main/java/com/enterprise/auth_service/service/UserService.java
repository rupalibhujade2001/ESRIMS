package com.enterprise.auth_service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.enterprise.auth_service.dto.RegisterRequest;
import com.enterprise.auth_service.dto.RegisterResponse;
import com.enterprise.auth_service.dto.UpdateUserRequest;
import com.enterprise.auth_service.dto.UserRequest;
import com.enterprise.auth_service.dto.UserResponse;

public interface UserService {

	public ResponseEntity<UserResponse> createUser(UserRequest user);

	public ResponseEntity<List<UserResponse>> getAllUsers();

	public ResponseEntity<UserResponse> getUserById(Long id);

	public ResponseEntity<UserResponse> updateUserById(Long id,UpdateUserRequest user);
	
	public ResponseEntity<UserResponse> getUserByEmail(String email);

	
}
