package com.enterprise.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

   
	private String message;
    private Long id;
    private String farmerName;
    private String email;
    
  //  private String message;


    public RegisterResponse() {
    }

    public RegisterResponse(String message) {
        this.message = message;
    }

  
}