package com.rishbootdev.subscriptionservice.entity;


import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Subscription {

    private Long id;

    private Long userId;

    private SubscriptionStatus status;

    private String stripeCustomerId;
    private String stripeSubscriptionId;

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
    private Boolean cancelAtPeriodEnd = false;

    private Instant createdAt;
    private Instant updatedAt;
}

