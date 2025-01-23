package com.deeps.newsportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.newsportal.dto.ArticlesDto;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.services.UserService;

@RequestMapping("/articles")
@RestController
public class ArticlesController {

	private final UserService userService;

	// create a submit articles

	public ArticlesController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/submit")
	public ResponseEntity<?> submitArticle(@RequestBody ArticlesDto articlesDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) authentication.getPrincipal();

		System.out.println(currentUser.getEmail());
		System.out.println(articlesDto);

		return ResponseEntity.ok("Done");

	}
}
