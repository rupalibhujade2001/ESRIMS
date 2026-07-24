package com.enterprise.auth_service.repository;

import java.util.Optional;

//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.auth_service.entity.User;

import jakarta.validation.constraints.Email;


public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	public boolean existsByEmail(String email);

}
