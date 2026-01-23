package com.rishbootdev.subscriptionservice.repository;


import com.rishbootdev.subscriptionservice.entity.Subscription;
import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByUserIdOrderByExpiresAtDesc(Long userId);

    List<Subscription> findSubscriptionByStatus(SubscriptionStatus status);
}

