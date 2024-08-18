package com.bloggerbackend.services;

import com.bloggerbackend.constants.AppConfigs;
import com.bloggerbackend.entities.Article;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.entities.Topic;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.mappers.ArticleMapper;
import com.bloggerbackend.models.request.ArticleReq;
import com.bloggerbackend.models.response.ArticleRes;
import com.bloggerbackend.models.response.TopicRes;
import com.bloggerbackend.repositories.elastics.ArticleELKRepository;
import com.bloggerbackend.repositories.mysql.ArticleRepository;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    public ArticleService(ArticleMapper articleMapper, ArticleRepository articleRepository, AuthorRepository authorRepository, AuthorService authorService) {
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    public List<ArticleRes> getArticles(Integer pageStart, Integer pageSize, String searchTerm, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(
                pageStart != null ? pageStart : AppConfigs.PAGE_START,
                pageSize != null ? pageSize : AppConfigs.PAGE_SIZE_DEFAULT
        );
        Page<Article> articlesPage;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            articlesPage = articleRepository.findByTitleContainingIgnoreCase(searchTerm, pageable);
        } else {
            articlesPage = articleRepository.findAll(pageable);
        }

        List<Article> articles = articlesPage.getContent();
        return articleMapper.mapToListArticleRes(articles, request);
    }

    @SneakyThrows
    public ArticleRes createArticle(ArticleReq articleReq, MultipartFile image, HttpServletRequest request) {
        Author author = authorService.getAuthorFromRequest(request);
        String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
        Article article = articleMapper.mapToArticle(articleReq);
        article.setAuthor(author);
        article.setImageBase64(imageBase64);
        return articleMapper.mapToArticleRes(articleRepository.save(article), request);
    }

    public ArticleRes getArticleById(Long id, HttpServletRequest request) {
        Article article = articleRepository.findById(id).get();
        return articleMapper.mapToArticleRes(article, request);
    }

    @SneakyThrows
    public ArticleRes updateArticle(Long articleId, ArticleReq articleReq, MultipartFile image, HttpServletRequest request) {
        String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
        Article article = articleMapper.mapToArticle(articleId, articleReq);
        article.setImageBase64(imageBase64);
        return articleMapper.mapToArticleRes(articleRepository.save(article), request);
    }

    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }

    public void addToSavedList(Long id, HttpServletRequest request) {
        Author author = authorService.getAuthorFromRequest(request);
        Article article = articleRepository.findById(id).get();
        if (!author.getSavedArticles().contains(article)) {
            author.getSavedArticles().add(article);
            authorRepository.save(author);
        }
    }

    public void removeFromSavedList(Long id, HttpServletRequest request) {
        Author author = authorService.getAuthorFromRequest(request);
        Article article = articleRepository.findById(id).get();
        if (author.getSavedArticles().contains(article)) {
            author.getSavedArticles().remove(article);
            authorRepository.save(author);
        }
    }

    public List<ArticleRes> getSavedArticles(HttpServletRequest request) {
        Author author = authorService.getAuthorFromRequest(request);
        List<Article> articles = articleRepository.findArticlesLikedByAuthor(author.getId());
        return articleMapper.mapToListArticleRes(articles, request);
    }

    public List<ArticleRes> getArticlesOfAuthor(Long authorId, HttpServletRequest request) {
        List<Article> articles = articleRepository.findArticlesByAuthorId(authorId);
        return articleMapper.mapToListArticleRes(articles, request);
    }

    public List<ArticleRes> getArticlesOfTopic(Long topicId, HttpServletRequest request) {
        List<Article> articles = articleRepository.findArticlesByTopicId(topicId);
        return articleMapper.mapToListArticleRes(articles, request);
    }
}
