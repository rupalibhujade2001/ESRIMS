package com.crop.inventory_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.crop.product_service.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login", "/auth/register").permitAll()
						.requestMatchers("/inventory/**","/inventory").hasAnyRole("FARMER","ADMIN").requestMatchers("/inventory/dashboard").hasAuthority("ADMIN")
						.requestMatchers("/inventory/dashboard/**").hasAuthority("ADMIN").anyRequest().authenticated());
//http.csrf(csrf -> csrf.disable())
//.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
