package com.deeps.newsportal.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deeps.newsportal.dto.ArticlesDto;
import com.deeps.newsportal.entity.Articles;
import com.deeps.newsportal.entity.User;
import com.deeps.newsportal.exceptions.ArticleException;
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

	public ArticlesDto convertToArticleDto(Articles article) {
		ArticlesDto articleDto = new ArticlesDto();
		articleDto.setTitleId(article.getId());
		articleDto.setTitle(article.getTitle());
		articleDto.setContent(readContentFromFile(article.getContent()));
		articleDto.setCreatedAt(article.getCreatedAt().toString());
		articleDto.setModifiedAt(article.getUpdatedAt().toString());
		articleDto.setAuthor(article.getUser().getFullName());
		return articleDto;
	}

	public List<ArticlesDto> getAllArticlesPublic() {
		return getAllArticles(articlesRepo.findAllActiveArticles());

	}

	// find article with article id with user attached
	public Articles findArticleById(Integer articleId) {
		Optional<Articles> optionalArticle = articlesRepo.findByArticleIdWithUser(articleId);
		Articles article = new Articles();
		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
		}
		return article;
	}

	/// Retrieve all articles 
	public List<ArticlesDto> getAllArticles(List<Articles> articles) {
		List<ArticlesDto> response = new ArrayList<>();
		for (Articles temp : articles) {

			ArticlesDto responseDto = new ArticlesDto();
			responseDto.setAuthor(temp.getUser().getFullName());
			responseDto.setTitle(temp.getTitle());
			responseDto.setTitleId(temp.getId());
			responseDto.setContent(readContentFromFile(temp.getContent()));
			responseDto.setCreatedAt(temp.getCreatedAt().toString());
			responseDto.setModifiedAt(temp.getUpdatedAt().toString());
			response.add(responseDto);

		}
		return response;
	}

	public List<Articles> findArticleAssociatedWithId(Integer id) {
		return articlesRepo.findAllByUserId(id);
	}

	// update an article
	public ArticlesDto updateArticle(ArticlesDto articleDto) {
		Articles article = this.findArticleById(articleDto.getTitleId());
		article.setTitle(articleDto.getTitle());
		article.setContent(saveContentToFile(articleDto.getContent(), article.getUser().getId(), article.getId()));
		articlesRepo.save(article);
		articleDto.setStatus(0);
		return articleDto;
	}

	// create an article
	public ArticlesDto createArticle(ArticlesDto articlesDto, User currentUser) {
		Articles newArticle = new Articles();
		newArticle.setTitle(articlesDto.getTitle());
		Optional<User> optionalUser = userRepo.findById(currentUser.getId());
		if (optionalUser.isPresent()) {
			newArticle.setUser(optionalUser.get());
			newArticle.setIsActive("A");
			newArticle = articlesRepo.save(newArticle);
			newArticle.setContent(saveContentToFile(articlesDto.getContent(), currentUser.getId(), newArticle.getId()));
			articlesRepo.save(newArticle);
		}
		return articlesDto;

	}

	public boolean verifyUserOfArticle(Integer articleId, User user) {
		Articles retrievedArticle = this.findArticleById(articleId);
		if (!retrievedArticle.equals(null) && retrievedArticle.getUser().getId().equals(user.getId())) {
			return true;
		}
		return false;
	}

	// delete article
	public void deleteArticle(Integer articleId) {
		Articles article = this.findArticleById(articleId);
		article.setIsActive("D");
		articlesRepo.save(article);
	}

	private String saveContentToFile(String content, Integer userId, Integer titleId) {
		String directoryPath = "articles" + userId + "/" + titleId;
		String filePath = directoryPath + "/content.txt";
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	private String readContentFromFile(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
