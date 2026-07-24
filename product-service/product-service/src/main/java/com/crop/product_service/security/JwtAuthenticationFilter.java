package com.crop.product_service.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	

	private final JwtService jwtService; 
	
	public JwtAuthenticationFilter(JwtService jwtService){
		this.jwtService=jwtService;
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
		System.out.println(jwt);
		String username = jwtService.extractUserName(jwt);
		System.out.println(username);
		String role=jwtService.extractRole(jwt);
		System.out.println(role);
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+role);
	   List<SimpleGrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));
	   
		 if(!jwtService.isTokenValid(jwt)) {
				System.out.println("INvalid token");
			 filterChain.doFilter(request, response);
	            return;
		 }
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null && jwtService.isTokenValid(jwt) )
		{
			System.out.println("valid token");
			UsernamePasswordAuthenticationToken  authentication= new UsernamePasswordAuthenticationToken(username,null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
		}
		filterChain.doFilter(request, response);

	}


}
