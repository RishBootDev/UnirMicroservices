package com.rishbootdev.notificationservice.event;


import lombok.Data;

@Data
public class PostCreatedEvent {
    Long creatorId;
    String content;
    Long postId;
}
