package com.rishbootdev.postservice.controller;

import com.rishbootdev.postservice.exceptions.BadRequestException;
import com.rishbootdev.postservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.postservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
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
