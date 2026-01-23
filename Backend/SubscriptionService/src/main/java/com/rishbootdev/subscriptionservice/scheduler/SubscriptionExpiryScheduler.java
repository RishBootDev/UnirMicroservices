package com.rishbootdev.subscriptionservice.scheduler;


import com.rishbootdev.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionExpiryScheduler {

    private final SubscriptionService subscriptionService;
    private final Logger log = LoggerFactory.getLogger(SubscriptionExpiryScheduler.class);

    // Run once per hour (adjust as needed)
    @Scheduled(cron = "0 0 * * * *")
    public void markExpired() {
        log.info("Running subscription expiry job...");
        subscriptionService.expireDueSubscriptions();
    }
}

