package com.rishbootdev.subscriptionservice.service;

import com.rishbootdev.subscriptionservice.entity.Subscription;
import org.springframework.stereotype.Service;


@Service
public interface SubscriptionService {

    Subscription createOrExtendSubscription(Long userId, int days);
    Subscription getLatestSubscription(Long userId);
    boolean cancelSubscription(Long userId);
    void expireDueSubscriptions();
}
