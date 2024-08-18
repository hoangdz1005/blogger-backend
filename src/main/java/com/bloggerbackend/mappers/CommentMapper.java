package com.bloggerbackend.mappers;

import com.bloggerbackend.entities.Article;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.entities.Comment;
import com.bloggerbackend.models.request.CommentReq;
import com.bloggerbackend.models.response.CommentRes;
import com.bloggerbackend.repositories.mysql.AccountRepository;
import com.bloggerbackend.repositories.mysql.ArticleRepository;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.repositories.mysql.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public CommentMapper(ArticleRepository articleRepository, AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    public Comment mapToComment(CommentReq from) {
        Comment to =new Comment();
        to.setContent(from.getContent());
        Article article = articleRepository.findById(from.getArticleId()).orElse(null);
        to.setArticle(article);
        to.setPublishedAt(LocalDateTime.now());
        return to;
    }
    public Comment mapToComment(Long id, CommentReq from) {
        Comment to = commentRepository.findById(id).get();
        to.setContent(from.getContent());
        to.setPublishedAt(LocalDateTime.now());
        return to;
    }
    public CommentRes mapToCommentRes(Comment from) {
        CommentRes to = new CommentRes();
        to.setId(from.getId());
        to.setContent(from.getContent());
        to.setArticleId(from.getArticle().getId());
        to.setPublishedAt(from.getPublishedAt());
        to.setAuthorId(from.getAuthor().getId());
        to.setAuthorName(from.getAuthor().getName());
        to.setAuthorAvatar(from.getAuthor().getAvatarImg());
        return to;
    }
    public List<CommentRes> mapToListCommentRes(List<Comment> from) {
        List<CommentRes> to = new ArrayList<CommentRes>();
        from.forEach(comment -> to.add(mapToCommentRes(comment)));
        return to;
    }
}
