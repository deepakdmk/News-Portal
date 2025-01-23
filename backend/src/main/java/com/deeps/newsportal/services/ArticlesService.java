package com.deeps.newsportal.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deeps.newsportal.dto.ArticlesDto;
import com.deeps.newsportal.entity.Articles;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.repositories.ArticlesRepository;
import com.deeps.newsportal.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ArticlesService {

	private ArticlesRepository articlesRepo;
	private UserRepository userRepo;

	public ArticlesService(ArticlesRepository articlesRepo, UserRepository userRepo) {
		this.articlesRepo = articlesRepo;
		this.userRepo = userRepo;
	}

	// create an article
	public Articles createArticle(ArticlesDto articlesDto, User user) {
		Articles newArticle = new Articles();
		newArticle.setTitle(articlesDto.getTitle());
		newArticle.setContent(articlesDto.getContent());

		user.setId(null);
		newArticle.setUser(user);
		articlesRepo.findby
		return articlesRepo.save(newArticle);
	}

	public String getArticlesByUser(User user) {
		return "works";
	}

}
