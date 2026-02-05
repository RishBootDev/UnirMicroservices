package com.rishbootdev.postsservice.dto;


import lombok.Data;

@Data
public class CommentCreateDto {
    private String content;
    private Long parentCommentId;
}
