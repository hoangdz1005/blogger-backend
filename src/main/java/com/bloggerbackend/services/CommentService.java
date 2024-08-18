package com.bloggerbackend.services;

import com.bloggerbackend.entities.Author;
import com.bloggerbackend.entities.Comment;
import com.bloggerbackend.mappers.CommentMapper;
import com.bloggerbackend.models.request.CommentReq;
import com.bloggerbackend.models.response.CommentRes;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.repositories.mysql.CommentRepository;
import com.bloggerbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final JwtUtils jwtUtils;
    private final AuthorRepository authorRepository;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, JwtUtils jwtUtils, AuthorRepository authorRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.jwtUtils = jwtUtils;
        this.authorRepository = authorRepository;
    }

    public CommentRes postComment(CommentReq commentReq, HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String emailAuthor = jwtUtils.extractUsername(accessToken);
        Author author = authorRepository.findByEmail(emailAuthor);
        Comment comment = commentMapper.mapToComment(commentReq);
        comment.setAuthor(author);
        return commentMapper.mapToCommentRes(commentRepository.save(comment));
    }

    public CommentRes editComment(Long id, CommentReq commentReq) {
        Comment comment = commentMapper.mapToComment(id, commentReq);
        return commentMapper.mapToCommentRes(commentRepository.save(comment));
    }

    public List<CommentRes> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return commentMapper.mapToListCommentRes(comments);
    }
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
