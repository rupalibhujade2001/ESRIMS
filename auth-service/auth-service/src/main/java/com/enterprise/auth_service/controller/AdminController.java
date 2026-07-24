package com.enterprise.auth_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.auth_service.dto.UpdateUserRequest;
import com.enterprise.auth_service.dto.UserRequest;
import com.enterprise.auth_service.dto.UserResponse;
import com.enterprise.auth_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest request) {

		return userService.createUser(request);
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<UserResponse>> getAllUsers() {

		return userService.getAllUsers();

	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id,@RequestBody UpdateUserRequest request){
		
		return userService.updateUserById(id,request);
		
		
	}
}
