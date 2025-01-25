package com.deeps.newsportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.newsportal.dto.ArticlesDto;
import com.deeps.newsportal.entity.Articles;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.services.ArticlesService;

@RequestMapping("/articles")
@RestController
public class ArticlesController {

	private final ArticlesService articleService;

	public ArticlesController(ArticlesService articleService) {
		this.articleService = articleService;
	}

	// Get all articles for public
	@GetMapping("/public")
	public ResponseEntity<?> getAllArticlesPublic() {
		List<ArticlesDto> articles = articleService.getAllArticlesPublic();
		if (articles == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Articles List is empty");
		}
		return ResponseEntity.ok(articles);
	}

	// Get a specific article by ID
	@GetMapping("/public/{id}")
	public ResponseEntity<?> getArticle(@PathVariable Integer id) {
		Articles article = articleService.findArticleById(id);
		if (article == null || article.getId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
		}

		// Convert to DTO
		ArticlesDto articleDto = articleService.convertToArticleDto(article);
		return ResponseEntity.ok(articleDto);
	}

	// Get all articles associated with currentUser
	@GetMapping("/articles/owned")
	public ResponseEntity<?> getAllArticles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();

		List<Articles> articles = articleService.findArticleAssociatedWithId(currentUser.getId());
		if (articles == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
		}
		List<ArticlesDto> articleDtos = articleService.getAllArticles(articles);
		return ResponseEntity.ok(articleDtos);
	}

	// Submit an article
	@PostMapping("/submit")
	public ResponseEntity<?> submitArticle(@RequestBody ArticlesDto articlesDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		ArticlesDto newArticle = articleService.createArticle(articlesDto, currentUser);
		return ResponseEntity.ok(newArticle);
	}

	// Update an article
	@PutMapping("/articles")
	public ResponseEntity<?> updateArticle(@RequestBody ArticlesDto articlesDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		if (articleService.verifyUserOfArticle(articlesDto.getTitleId(), currentUser)) {
			articleService.updateArticle(articlesDto);
			return ResponseEntity.ok("Article updated successfully");
		}
		throw new AccessDeniedException("You do not own this article");
	}

	// Delete an article
	@DeleteMapping("/articles/{id}")
	public ResponseEntity<?> deleteArticle(@PathVariable Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		if (articleService.verifyUserOfArticle(id, currentUser)) {
			articleService.deleteArticle(id);
			return ResponseEntity.ok("Article deleted successfully");
		}
		throw new AccessDeniedException("You do not own this article");
	}

}
