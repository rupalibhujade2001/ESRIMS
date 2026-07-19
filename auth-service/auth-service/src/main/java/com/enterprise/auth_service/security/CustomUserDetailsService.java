package com.enterprise.auth_service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enterprise.auth_service.entity.User;
import com.enterprise.auth_service.exception.UserNotFoundException;
import com.enterprise.auth_service.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

			User user = null;
			try {
				user = userRepository.findByEmail(username).orElseThrow(()->new UserNotFoundException("User not found",HttpStatus.NOT_FOUND));
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 return org.springframework.security.core.userdetails.User
		        .withUsername(user.getEmail())
		        .password(user.getPassowrd())
		        .authorities(user.getRole().name())
		        .build();
	}

}
