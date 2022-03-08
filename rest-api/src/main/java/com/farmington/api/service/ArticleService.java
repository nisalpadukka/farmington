package com.farmington.api.service;

import com.farmington.api.entity.Article;
import com.farmington.api.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;

    public Article saveArticle(Article article) {
        return repository.save(article);
    }

    public List<Article> saveArticles(List<Article> Articles) {
        return repository.saveAll(Articles);
    }

    public List<Article> getArticles() {
        return repository.findAll();
    }

    public Article getArticleById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Article getArticleByName(String name) {
        return repository.findById(name);
    }

    public String deleteArticle(int id) {
        repository.deleteById(id);
        return "Article removed !! " + id;
    }

    public Article updateArticle(Article article) {
        Article existingArticle = repository.findById(article.getId()).orElse(null);
        existingArticle.setDescription(article.getDescription());
        return repository.save(existingArticle);
    }
}
