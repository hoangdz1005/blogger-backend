package com.bloggerbackend.mappers;

import com.bloggerbackend.entities.Article;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.entities.Comment;
import com.bloggerbackend.entities.Topic;
import com.bloggerbackend.enums.ArticleStatus;
import com.bloggerbackend.models.request.ArticleReq;
import com.bloggerbackend.models.response.ArticleRes;
import com.bloggerbackend.repositories.mysql.ArticleRepository;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.repositories.mysql.CommentRepository;
import com.bloggerbackend.repositories.mysql.TopicRepository;
import com.bloggerbackend.services.AuthorService;
import com.bloggerbackend.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleMapper {
    private final TopicRepository topicRepository;
    private final AuthorMapper authorMapper;
    private final TopicMapper topicMapper;
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;

    public ArticleMapper(TopicRepository topicRepository, AuthorMapper authorMapper, TopicMapper topicMapper, ArticleRepository articleRepository, AuthorService authorService) {
        this.topicRepository = topicRepository;
        this.authorMapper = authorMapper;
        this.topicMapper = topicMapper;
        this.articleRepository = articleRepository;
        this.authorService = authorService;
    }

    public Article mapToArticle(ArticleReq from) {
        Article to = new Article();
        to.setTitle(from.getTitle());
        to.setSlug(StringUtils.mapToSlug(from.getTitle()));
        to.setContent(from.getContent());
        to.setStatus(ArticleStatus.PUBLISHED);
        to.setSummary(from.getSummary());
        to.setMinRead(from.getMinRead());
        to.setCreatedAt(LocalDateTime.now());
        to.setUpdatedAt(LocalDateTime.now());
        to.setPublishedAt(LocalDateTime.now());
        Topic topic = topicRepository.findById(from.getTopicId()).orElse(null);
        to.setTopic(topic);
        return to;
    }

    public Article mapToArticle(Long id, ArticleReq from) {
        Article to = articleRepository.findById(id).get();
        to.setTitle(from.getTitle());
        to.setSlug(StringUtils.mapToSlug(from.getTitle()));
        to.setContent(from.getContent());
        to.setSummary(from.getSummary());
        to.setMinRead(from.getMinRead());
        to.setUpdatedAt(LocalDateTime.now());
        Topic topic = topicRepository.findById(from.getTopicId()).orElse(null);
        to.setTopic(topic);
        return to;
    }

    public ArticleRes mapToArticleRes(Article from, HttpServletRequest request) {
        ArticleRes to = new ArticleRes();
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        to.setSlug(from.getSlug());
        to.setSummary(from.getSummary());
        to.setMinRead(from.getMinRead());
        to.setImageBase64(from.getImageBase64());
        to.setStatus(from.getStatus().name());
        to.setCreatedAt(from.getCreatedAt());
        to.setUpdatedAt(from.getUpdatedAt());
        to.setPublishedAt(from.getPublishedAt());
        to.setContent(from.getContent());
        to.setNumberOfComments(from.getComments().size());
        Author author = authorService.getAuthorFromRequest(request);
        to.setSaved(author.getSavedArticles().contains(from));
        to.setTopic(topicMapper.mapToTopicRes(from.getTopic()));
        to.setAuthor(authorMapper.mapToAuthorRes(from.getAuthor()));
        return to;
    }

    public List<ArticleRes> mapToListArticleRes(List<Article> from, HttpServletRequest request) {
        List<ArticleRes> to = new ArrayList<>();
        from.forEach(article -> to.add(mapToArticleRes(article, request)));
        return to;
    }
}
