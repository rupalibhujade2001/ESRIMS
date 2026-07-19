package com.enterprise.auth_service.service;

import com.enterprise.auth_service.dto.LoginRequest;
import com.enterprise.auth_service.dto.LoginResponse;
import com.enterprise.auth_service.dto.RegisterRequest;
import com.enterprise.auth_service.dto.RegisterResponse;
import com.enterprise.auth_service.exception.UserNotFoundException;

public interface AuthService {
	LoginResponse login(LoginRequest request) throws UserNotFoundException;
	RegisterResponse register(RegisterRequest user);

	
}
