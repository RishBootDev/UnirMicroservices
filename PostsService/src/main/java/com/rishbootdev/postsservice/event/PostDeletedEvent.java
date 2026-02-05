package com.rishbootdev.postsservice.event;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDeletedEvent {
    private Long postId;
    private Long deletedByUserId;
}

