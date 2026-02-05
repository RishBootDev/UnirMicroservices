package com.rishbootdev.notificationservice.event;


import lombok.Data;

@Data
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
