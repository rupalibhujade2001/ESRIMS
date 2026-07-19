package com.enterprise.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.auth_service.dto.LoginRequest;
import com.enterprise.auth_service.dto.LoginResponse;
import com.enterprise.auth_service.dto.RegisterRequest;
import com.enterprise.auth_service.dto.RegisterResponse;
import com.enterprise.auth_service.entity.User;
import com.enterprise.auth_service.exception.UserNotFoundException;
import com.enterprise.auth_service.service.AuthService;
import com.enterprise.auth_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user) {
		RegisterResponse a = authService.register(user);
		return ResponseEntity.ok(a);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		LoginResponse response = null;
		try {
			response = authService.login(request);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);

	}
	
	

}
