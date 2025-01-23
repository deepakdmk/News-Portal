package com.deeps.newsportal.services;

import org.springframework.stereotype.Service;

import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.repositories.ArticlesRepository;
import com.deeps.newsportal.repositories.UserRepository;

@Service
public class ArticlesService {

	private ArticlesRepository articlesRepo;
	private UserRepository userRepo;

	public ArticlesService(ArticlesRepository articlesRepo, UserRepository userRepo) {
		this.articlesRepo = articlesRepo;
		this.userRepo = userRepo;
	}

	public String getArticlesByUser(User user) {
		return "works";
	}

}
