package com.deeps.newsportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.newsportal.entity.Articles;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Integer> {

}
