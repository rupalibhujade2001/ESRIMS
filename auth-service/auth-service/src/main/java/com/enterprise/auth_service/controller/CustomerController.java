	package com.enterprise.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.auth_service.dto.UserResponse;
import com.enterprise.auth_service.service.AuthService;
import com.enterprise.auth_service.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("farmer")
@RequiredArgsConstructor
public class CustomerController {
	
	@Autowired
	private UserService userService;
	
	public CustomerController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/userDetails/{email}")
	public ResponseEntity<UserResponse> getUserDetailsByFarmerEmail( @Valid @PathVariable  @Email String email){
		//userService.getUserByEmail(email);
		return userService.getUserByEmail(email);
	}

}
