package com.crop.product_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

//import com.crop.product_service.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	
private final JwtAuthenticationFilter jwtAuthenticationFilter;



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
				
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth

		        .requestMatchers(HttpMethod.GET, "/products/**")
		        .permitAll()

		        .requestMatchers(HttpMethod.POST, "/products")
		        .hasAnyRole("ADMIN", "FARMER")

		        .requestMatchers(HttpMethod.PUT, "/products/**")
		        .hasAnyRole("ADMIN", "FARMER")

		        .requestMatchers(HttpMethod.DELETE, "/products/**")
		        .hasAnyRole("ADMIN","FARMER")

		        .anyRequest()
		        .authenticated()
		);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}


}
