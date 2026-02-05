package com.rishbootdev.postsservice.dto;

import com.rishbootdev.postsservice.enums.PostType;
import com.rishbootdev.postsservice.enums.PostVisibility;
import lombok.Data;


@Data
public class PostCreateRequestDto {
    private String content;
    private String mediaUrl;
    private PostType type;
    private PostVisibility visibility;
}
