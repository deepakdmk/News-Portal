package com.deeps.newsportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.newsportal.dto.ArticlesDto;
import com.deeps.newsportal.entity.Articles;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.services.ArticlesService;
import com.deeps.newsportal.services.UserService;

@RequestMapping("/articles")
@RestController
public class ArticlesController {

	private final UserService userService;
	private final ArticlesService articleService;

	// create a submit articles
	public ArticlesController(UserService userService, ArticlesService articleService) {
		super();
		this.userService = userService;
		this.articleService = articleService;
	}

	@PostMapping("/submit")
	public ResponseEntity<?> submitArticle(@RequestBody ArticlesDto articlesDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User currentUser = (User) authentication.getPrincipal();

			Articles respondedArticle = articleService.createArticle(articlesDto, currentUser);
			if (respondedArticle.getId() != 0) {
				return ResponseEntity.ok("Done");
			} else {
				return ResponseEntity.ok("Failed to create");
			}
		}

		return ResponseEntity.ok("Failed");
	}

}
