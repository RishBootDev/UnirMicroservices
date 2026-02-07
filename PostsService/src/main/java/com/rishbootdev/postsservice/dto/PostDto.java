package com.rishbootdev.postsservice.dto;

import com.rishbootdev.postsservice.enums.PostType;
import com.rishbootdev.postsservice.enums.PostVisibility;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PostDto {
    private Long id;
    private Long userId;
    private String content;
    private String mediaUrl;
    private PostType type;
    private PostVisibility visibility;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private boolean likedByCurrentUser;
}
