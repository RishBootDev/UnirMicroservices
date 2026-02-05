package com.rishbootdev.postsservice.controller;


import com.rishbootdev.postsservice.dto.CommentCreateDto;
import com.rishbootdev.postsservice.entity.PostComment;
import com.rishbootdev.postsservice.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final PostCommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> comment(
            @PathVariable Long postId,
            @RequestBody CommentCreateDto dto) {
        commentService.addComment(postId, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<PostComment>> getComments(
            @PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

