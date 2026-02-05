package com.rishbootdev.postsservice.controller;

import com.rishbootdev.postsservice.exceptions.BadRequestException;
import com.rishbootdev.postsservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.postsservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/likes")
@RequiredArgsConstructor
public class LikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) throws BadRequestException, ResourceNotFoundException {
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) throws BadRequestException, ResourceNotFoundException {
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}
