package com.bloggerbackend.apis;

import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.AuthorReq;
import com.bloggerbackend.models.response.AuthorRes;
import com.bloggerbackend.services.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class AuthorResources {

    private final AuthorService authorService;

    public AuthorResources(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorRes> getAuthor(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @PutMapping("/authors/profile")
    public ResponseEntity<AuthorRes> updateAuthorProfile(
            @RequestBody AuthorReq authorReq, HttpServletRequest request) {
        return new ResponseEntity<>(authorService.updateAuthorProfile(authorReq, request), HttpStatus.OK);
    }

    @PostMapping("/authors/{id}/follow")
    public ResponseEntity<Void> followAuthor(@PathVariable Long id, HttpServletRequest request) {
        authorService.followOtherAuthor(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authors/{id}/unfollow")
    public ResponseEntity<Void> unfollowAuthor(@PathVariable Long id, HttpServletRequest request) {
        authorService.unfollowOtherAuthor(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authors/following")
    public ResponseEntity<List<AuthorRes>> getFollowing(HttpServletRequest request) {
        return new ResponseEntity<>(authorService.getFollowing(request), HttpStatus.OK);
    }

    @GetMapping("/authors/followers")
    public ResponseEntity<List<AuthorRes>> getFollowers(HttpServletRequest request) {
        return new ResponseEntity<>(authorService.getFollowers(request), HttpStatus.OK);
    }
}
