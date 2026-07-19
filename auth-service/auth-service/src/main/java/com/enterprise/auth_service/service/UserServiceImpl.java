package com.enterprise.auth_service.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeOperationsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enterprise.auth_service.dto.RegisterRequest;
import com.enterprise.auth_service.dto.RegisterResponse;
import com.enterprise.auth_service.dto.UpdateUserRequest;
import com.enterprise.auth_service.dto.UserRequest;
import com.enterprise.auth_service.dto.UserResponse;
import com.enterprise.auth_service.entity.Role;
import com.enterprise.auth_service.entity.User;
import com.enterprise.auth_service.exception.EmailAlreadyExistsException;
import com.enterprise.auth_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<UserResponse> createUser(UserRequest user) {
		Optional<User> response = userRepository.findByEmail(user.getEmail());
		UserResponse userResposne = new UserResponse();
		if (response.isEmpty()) {
			User NewUser = new User();
			NewUser.setUserName(user.getUsername());
			NewUser.setEmail(user.getEmail());
			NewUser.setPassowrd(passwordEncoder.encode(user.getPassword()));
			NewUser.setRole(user.getRole());
			NewUser.setFarmerName(user.getFarmerName());
			NewUser.setPhone(user.getPhone());
			User dbResponse = userRepository.save(NewUser);
			if (dbResponse != null) {
				userResposne.setUsername(dbResponse.getUserName());
				userResposne.setMessage("User Added successfully");
				userResposne.setId(dbResponse.getId());
				return ResponseEntity.ok(userResposne);
			} else {
				throw new RuntimeException("Something went wrong");
			}

		} else {
			userResposne.setMessage("User Alreday Exist");
			userResposne.setUsername(response.get().getEmail());

			return ResponseEntity.ok(userResposne);
		}
	}

	@Override
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<User> response = userRepository.findAll();
		return ResponseEntity.ok(response.stream().map(this::mapToResponse).toList());
		
	}
	private UserResponse mapToResponse(User user) {
		
		UserResponse response=new UserResponse();
		response.setId(user.getId());
		response.setUsername(user.getUserName());
		
		return response;
		
	}

	@Override
	public ResponseEntity<UserResponse> getUserById(Long id) {
		Optional<User> response = userRepository.findById(id);
		return ResponseEntity.ok(mapToResponse(response.get()));
	}

	@Override
	public ResponseEntity<UserResponse> updateUserById(Long id,UpdateUserRequest request) {
		Optional<User> user = userRepository.findById(id);
		UserResponse response=new UserResponse();
		if(!user.isEmpty()) {
			User user1=user.get();
		//	user1.setId(user1.getId());
			user1.setEmail(request.getEmail());
			user1.setUserName(request.getUsername());
			user1.setRole(request.getRole());
			userRepository.save(user1);
			response.setMessage("User update successfullly");
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserResponse> getUserByEmail(String email) {
		System.out.println("User");		

		Optional<User> user1 = userRepository.findByEmail(email);
		System.out.println("User");		

		User user=user1.get();
		System.out.println("User"+user.getEmail());		
		UserResponse response=new UserResponse();
		response.setEmail(user.getEmail());
		response.setFarmerName(user.getFarmerName());
		response.setPhone(user.getPhone());
		return ResponseEntity.ok(response);
	}

}
