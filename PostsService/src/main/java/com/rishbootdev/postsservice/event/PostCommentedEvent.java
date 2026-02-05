package com.rishbootdev.postsservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCommentedEvent {
    Long postId;
    Long commentedByUserId;
}

