package com.crop.product_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.crop.product_service.dto.UserDetailsResponse;
//import com.enterprise.auth_service.dto.UserResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

    @GetMapping("/farmer/userDetails/{email}")
	UserDetailsResponse getUserDetailsByFarmerEmail( @Valid @PathVariable  @Email String email) ;
		
		
	
}
