package com.enterprise.auth_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enterprise.auth_service.dto.LoginRequest;
import com.enterprise.auth_service.dto.LoginResponse;
import com.enterprise.auth_service.dto.RegisterRequest;
import com.enterprise.auth_service.dto.RegisterResponse;
import com.enterprise.auth_service.entity.Role;
import com.enterprise.auth_service.entity.User;
import com.enterprise.auth_service.exception.EmailAlreadyExistsException;
import com.enterprise.auth_service.exception.UserNotFoundException;
import com.enterprise.auth_service.repository.UserRepository;
import com.enterprise.auth_service.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse login(LoginRequest request) throws UserNotFoundException {

		Optional<User> user = userRepository.findByEmail(request.getEmail());
		LoginResponse response = new LoginResponse();

		if (!user.isEmpty()) {
			if (passwordEncoder.matches(request.getPassword(), user.get().getPassowrd()) && request.getRole().equals(user.get().getRole())) {
				String token = jwtService.generateToken(request.getEmail(),request.getRole().name());
				response.setToken(token);
				response.setType("Bearer");
				response.setEmail(request.getEmail());
				response.setRole(user.get().getRole());
				response.setMessage("Login Successful");
			} else {
				throw new RuntimeException("Invalid password");
			}

		} else {
			throw new UserNotFoundException("User not fount", HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@Override
	public RegisterResponse register(RegisterRequest reuqest) {

		User user = new User();
		RegisterResponse rr = new RegisterResponse();
		user.setUserName(reuqest.getUsername());
		user.setEmail(reuqest.getEmail());
		user.setPassowrd(passwordEncoder.encode(reuqest.getPassword()));
		user.setRole(Role.FARMER);
		user.setFarmerName(reuqest.getFarmerName());
		user.setPhone(reuqest.getPhone());

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("User Alreday existed", HttpStatus.FOUND);
		}
		User userResponse = userRepository.save(user);

		rr.setFarmerName(userResponse.getFarmerName());
		rr.setEmail(userResponse.getEmail());
		rr.setMessage("User regisered successfully.");

		return rr;
	}

}
