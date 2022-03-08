package com.farmington.api.repository;

import com.farmington.api.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Article findById(String id);
}

