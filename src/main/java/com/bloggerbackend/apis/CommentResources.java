package com.bloggerbackend.apis;


import com.bloggerbackend.constants.AppConstants;
import com.bloggerbackend.models.request.CommentReq;
import com.bloggerbackend.models.response.CommentRes;
import com.bloggerbackend.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_API_URL)
public class CommentResources {
    private final CommentService commentService;

    public CommentResources(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentRes> postComment(@RequestBody CommentReq commentReq, HttpServletRequest request) {
        return new ResponseEntity<>(commentService.postComment(commentReq, request), HttpStatus.OK);
    }
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentRes> editComment(@PathVariable Long id, @RequestBody CommentReq commentReq) {
        return new ResponseEntity<>(commentService.editComment(id, commentReq), HttpStatus.OK);
    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
