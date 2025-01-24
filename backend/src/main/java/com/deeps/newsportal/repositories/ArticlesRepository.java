package com.deeps.newsportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deeps.newsportal.entity.Articles;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Integer> {

    @Query("SELECT a FROM Articles a JOIN FETCH a.user WHERE a.id = :articleId AND a.isActive = 'A'")
    Optional<Articles> findByArticleIdWithUser(@Param("articleId") Integer articleId);

    @Query("SELECT a FROM Articles a JOIN FETCH a.user WHERE a.user.id = :userId AND a.isActive = 'A'")
    List<Articles> findAllByUserId(@Param("userId") Integer userId);

	@Query("SELECT a FROM Articles a WHERE a.isActive = 'A'")
    List<Articles> findAllActiveArticles();

}
