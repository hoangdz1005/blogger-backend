package com.bloggerbackend.apis;

import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.ArticleReq;
import com.bloggerbackend.models.response.ArticleRes;
import com.bloggerbackend.models.response.CommentRes;
import com.bloggerbackend.services.ArticleService;
import com.bloggerbackend.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class ArticleResources {

    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleResources(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleRes> addArticle(@ModelAttribute ArticleReq articleReq,
                                                 @RequestPart("image") MultipartFile image,
                                                 HttpServletRequest request) {
        return new ResponseEntity<>(articleService.createArticle(articleReq, image, request), HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleRes>> getArticles(@RequestParam(required = false) Integer pageStart,
                                                        @RequestParam(required = false) Integer pageSize,
                                                        @RequestParam(required = false) String searchTerm,
                                                        HttpServletRequest request) {
        return new ResponseEntity<>(articleService.getArticles(pageStart, pageSize, searchTerm, request), HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleRes> getArticle(@PathVariable Long id,
                                                 HttpServletRequest request) {
        return new ResponseEntity<>(articleService.getArticleById(id, request), HttpStatus.OK);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleRes> updateArticle( @PathVariable Long id,
                                                     @ModelAttribute ArticleReq articleReq,
                                                     @RequestPart("image") MultipartFile image,
                                                     HttpServletRequest request) {
        return new ResponseEntity<>(articleService.updateArticle(id, articleReq, image,request), HttpStatus.OK);
    }
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles/{id}/comments")
    public ResponseEntity<List<CommentRes>> getCommentsArticle(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentsByArticle(id), HttpStatus.OK);
    }

    @PostMapping("/articles/{id}/saved")
    public ResponseEntity<Void> savedArticle (@PathVariable Long id, HttpServletRequest request) {
        articleService.addToSavedList(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/articles/{id}/unsaved")
    public ResponseEntity<Void> unsavedArticle (@PathVariable Long id, HttpServletRequest request) {
        articleService.removeFromSavedList(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles/saved")
    public ResponseEntity<List<ArticleRes>> getSavedArticles (HttpServletRequest request) {
        return new ResponseEntity<>(articleService.getSavedArticles(request), HttpStatus.OK);
    }

    @GetMapping("/articles/authors/{id}")
    public ResponseEntity<List<ArticleRes>> getArticlesOfAuthor (@PathVariable Long id,  HttpServletRequest request) {
        return new ResponseEntity<>(articleService.getArticlesOfAuthor(id, request), HttpStatus.OK);
    }

    @GetMapping("/articles/topics/{id}")
    public ResponseEntity<List<ArticleRes>> getArticlesOfTopic (@PathVariable Long id,  HttpServletRequest request) {
        return new ResponseEntity<>(articleService.getArticlesOfTopic(id, request), HttpStatus.OK);
    }
}
