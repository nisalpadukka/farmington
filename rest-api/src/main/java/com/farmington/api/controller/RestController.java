package com.farmington.api.controller;

import com.farmington.api.entity.Article;
import com.farmington.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class RestController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/article")
    public Article addArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @GetMapping("/article")
    public List<Article> findAllProducts() {
        return articleService.getArticles();
    }

    @PutMapping("/update")
    public Article updateProduct(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/article/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return articleService.deleteArticle(id);
    }
}
