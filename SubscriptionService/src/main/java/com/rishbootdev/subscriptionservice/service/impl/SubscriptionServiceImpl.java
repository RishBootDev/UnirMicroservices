package com.rishbootdev.subscriptionservice.service.impl;

import com.rishbootdev.subscriptionservice.entity.Subscription;
import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import com.rishbootdev.subscriptionservice.repository.SubscriptionRepository;
import com.rishbootdev.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public Subscription createOrExtendSubscription(Long userId, int days) {
        Instant now = Instant.now();
        Optional<Subscription> latestOpt = subscriptionRepository.findFirstByUserIdOrderByExpiresAtDesc(userId);

        if (latestOpt.isPresent() && latestOpt.get().getStatus() == SubscriptionStatus.ACTIVE && latestOpt.get().getExpiresAt().isAfter(now)) {
            Subscription existing = latestOpt.get();
            existing.setExpiresAt(existing.getExpiresAt().plus(days, ChronoUnit.DAYS));
            return subscriptionRepository.save(existing);
        } else {

            Subscription sub = new Subscription();
            sub.setUserId(userId);
            sub.setStartAt(now);
            sub.setExpiresAt(now.plus(days, ChronoUnit.DAYS));
            sub.setStatus(SubscriptionStatus.ACTIVE);
            return subscriptionRepository.save(sub);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Subscription getLatestSubscription(Long userId) {
        return subscriptionRepository.findFirstByUserIdOrderByExpiresAtDesc(userId).orElse(null);
    }

    @Override
    @Transactional
    public boolean cancelSubscription(Long userId) {
        Optional<Subscription> opt = subscriptionRepository.findFirstByUserIdOrderByExpiresAtDesc(userId);
        if (opt.isPresent()) {
            Subscription s = opt.get();
            s.setStatus(SubscriptionStatus.CANCELED);
            s.setExpiresAt(Instant.now());
            subscriptionRepository.save(s);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void expireDueSubscriptions() {
        Instant now = Instant.now();
        List<Subscription> activeSubs = subscriptionRepository.findSubscriptionByStatus(SubscriptionStatus.ACTIVE);
        for (Subscription s : activeSubs) {
            if (s.getExpiresAt().isBefore(now) || s.getExpiresAt().equals(now)) {
                s.setStatus(SubscriptionStatus.EXPIRED);
            }
        }
        subscriptionRepository.saveAll(activeSubs);
    }
}
