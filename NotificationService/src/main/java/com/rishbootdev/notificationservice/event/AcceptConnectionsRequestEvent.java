package com.rishbootdev.notificationservice.event;


import lombok.Data;

@Data
public class AcceptConnectionsRequestEvent {
    private Long senderId;
    private Long receiverId;
}
