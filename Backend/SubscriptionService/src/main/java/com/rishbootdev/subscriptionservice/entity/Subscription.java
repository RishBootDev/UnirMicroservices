package com.rishbootdev.subscriptionservice.entity;


import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated
    private SubscriptionStatus status;
    private Instant startAt;
    private Instant expiresAt;
}

