package com.enterprise.auth_service.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	private final JwtService jwtService; 
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService,CustomUserDetailsService customUserDetailsService){
		this.jwtService=jwtService;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("========== JWT FILTER EXECUTED ==========");
		System.out.println(request.getRequestURI());
		
		String authHeader = request.getHeader("Authorization");
		if(authHeader==null || !authHeader.startsWith("Bearer ")  )
		{
			System.out.println("**"+request.getRequestURI());
			filterChain.doFilter(request, response);
            return;
		}
		String jwt=authHeader.substring(7);
		String username = jwtService.extractUserName(jwt);
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null )
		{
		UserDetails user=customUserDetailsService.loadUserByUsername(username);
		if(jwtService.isTokenValid(jwt, user))
		{
			UsernamePasswordAuthenticationToken  authentication= new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		}
		filterChain.doFilter(request, response);

	}

}
