package com.rishbootdev.subscriptionservice.dto;

import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import lombok.Data;

@Data
public class SubscriptionStatusDto {
    private Long userId;
    private boolean active;
    private SubscriptionStatus status;
    private String expiresAt;
}

