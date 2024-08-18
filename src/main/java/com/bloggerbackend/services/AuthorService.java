package com.bloggerbackend.services;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.exceptions.EntityNotFoundException;
import com.bloggerbackend.mappers.AuthorMapper;
import com.bloggerbackend.models.request.AuthorReq;
import com.bloggerbackend.models.response.AuthorRes;
import com.bloggerbackend.repositories.mysql.AuthorRepository;
import com.bloggerbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final JwtUtils jwtUtils;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, JwtUtils jwtUtils) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.jwtUtils = jwtUtils;
    }

    public AuthorRes getAuthorById(Long id) {
        Author author = authorRepository.findById(id).get();
        return authorMapper.mapToAuthorRes(author);
    }

    public AuthorRes updateAuthorProfile(@RequestBody AuthorReq authorReq, HttpServletRequest request) {
        Author author = getAuthorFromRequest(request);
        authorMapper.updateAuthor(author, authorReq);
        authorRepository.save(author);
        return authorMapper.mapToAuthorRes(author);
    }

    public Author getAuthorFromRequest(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String emailAuthor = jwtUtils.extractUsername(accessToken);
        return authorRepository.findByEmail(emailAuthor);
    }

    // Theo dõi
    public void followOtherAuthor(Long idToFollow, HttpServletRequest request) {
        Author currentAuthor = getAuthorFromRequest(request);
        Author authorToFollow = authorRepository.findById(idToFollow).get();
        currentAuthor.getFollowing().add(authorToFollow);
        authorRepository.save(currentAuthor);
    }

    // Hủy theo dõi
    public void unfollowOtherAuthor(Long idToUnfollow, HttpServletRequest request) {
        Author currentAuthor = getAuthorFromRequest(request);
        Author authorToUnfollow = authorRepository.findById(idToUnfollow).get();
        currentAuthor.getFollowing().remove(authorToUnfollow);
        authorRepository.save(currentAuthor);
    }

    public List<AuthorRes> getFollowing (HttpServletRequest request) {
        Author currentAuthor = getAuthorFromRequest(request);
        return authorMapper.mapToListAuthorRes(currentAuthor.getFollowing());
    }

    public List<AuthorRes> getFollowers (HttpServletRequest request) {
        Author currentAuthor = getAuthorFromRequest(request);
        return authorMapper.mapToListAuthorRes(currentAuthor.getFollowers());
    }
}
