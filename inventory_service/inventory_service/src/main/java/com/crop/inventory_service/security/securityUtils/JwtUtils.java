package com.crop.inventory_service.security.securityUtils;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {


	public String getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication.getName();
	}

	public List<String> getLoggedInUSerRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	}
	

}
