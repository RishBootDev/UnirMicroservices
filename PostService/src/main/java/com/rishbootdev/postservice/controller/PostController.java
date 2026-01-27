package com.rishbootdev.postservice.controller;


import com.rishbootdev.postservice.dto.PostCreateRequestDto;
import com.rishbootdev.postservice.dto.PostDto;
import com.rishbootdev.postservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.postservice.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto) {
        PostDto createdPost = postsService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) throws ResourceNotFoundException {
        PostDto postDto = postsService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }


    // field pe post time ke basis pe sort
    @GetMapping("/postByField/{field}")
    public ResponseEntity<?> top10PostByField(@PathVariable String field){
        return ResponseEntity.ok(postsService.getPostsByField(field));
    }
}