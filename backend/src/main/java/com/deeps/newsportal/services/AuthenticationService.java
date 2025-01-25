package com.deeps.newsportal.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deeps.newsportal.dto.LoginUserDto;
import com.deeps.newsportal.dto.RegisterUserDto;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.repositories.UserRepository;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User create(RegisterUserDto input) {
		User user = new User();
		user.setEmail(input.getEmail());
		user.setFullName(input.getFullName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));

		return userRepository.save(user);
	}

	public boolean checkForDuplicates(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			return true;
		}
		return false;

	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}

	public RegisterUserDto createResponse(User user) {
		RegisterUserDto responseUser = new RegisterUserDto();
		responseUser.setEmail(user.getEmail());
		responseUser.setFullName(user.getFullName());
		return responseUser;
	}
}