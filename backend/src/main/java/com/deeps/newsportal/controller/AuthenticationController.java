package com.deeps.newsportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.newsportal.dto.LoginResponse;
import com.deeps.newsportal.dto.LoginUserDto;
import com.deeps.newsportal.dto.RegisterUserDto;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.exceptions.UserException;
import com.deeps.newsportal.services.AuthenticationService;
import com.deeps.newsportal.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
		if (!(authenticationService.checkForDuplicates(registerUserDto.getEmail()))) {
			User registeredUser = authenticationService.create(registerUserDto);
			RegisterUserDto registration = authenticationService.createResponse(registeredUser);
			return ResponseEntity.ok(registration);
		}
		throw new UserException("User is registered, please login");

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}
}