package com.deeps.newsportal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.newsportal.dto.UserDto;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);

		return users;
	}

	public UserDto convertUserToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setFullName(user.getFullName());
		userDto.setEmail(user.getEmail());
		userDto.setProfilePic(user.getProfilePic());
		userDto.setCreatedAt(user.getCreatedAt().toString());
		userDto.setUpdatedAt(user.getUpdatedAt().toString());
		userDto.setArticles(user.getArticles());

		return userDto;
	}

}