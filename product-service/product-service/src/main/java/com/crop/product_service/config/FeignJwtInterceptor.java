package com.crop.product_service.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignJwtInterceptor implements RequestInterceptor {

	    @Override
	    public void apply(RequestTemplate template) {

	        Authentication authentication =
	                SecurityContextHolder.getContext().getAuthentication();	
	        System.out.println("Feign Interceptor Called");
	        if (authentication != null
	                && authentication.getCredentials() instanceof String token) {

	            template.header("Authorization", "Bearer " + token);
	            System.out.println("JWT Token = " + token);
	        }
	    }

	}


