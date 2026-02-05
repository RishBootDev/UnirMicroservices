package com.rishbootdev.postsservice.controller;



import com.rishbootdev.postsservice.dto.PostDto;
import com.rishbootdev.postsservice.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostDto> create(
            @RequestBody PostDto dto) {
        return ResponseEntity.ok(postsService.createPost(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.getPost(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<org.springframework.data.domain.Page<PostDto>> getUserPosts(
            @PathVariable Long userId,
            org.springframework.data.domain.Pageable pageable
    ) {
        return ResponseEntity.ok(postsService.getUserPosts(userId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postsService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
