package com.rishbootdev.postsservice.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PostDto {
    private Long id;
    private Long userId;
    private String content;
    private String mediaUrl;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createdAt;
}
