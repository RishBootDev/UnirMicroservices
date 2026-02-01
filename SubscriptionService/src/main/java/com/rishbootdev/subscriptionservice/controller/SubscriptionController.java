package com.rishbootdev.subscriptionservice.controller;

import com.rishbootdev.subscriptionservice.dto.SubscriptionStatusDto;
import com.rishbootdev.subscriptionservice.entity.Subscription;
import com.rishbootdev.subscriptionservice.enums.SubscriptionStatus;
import com.rishbootdev.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/status/{userId}")
    public ResponseEntity<SubscriptionStatusDto> status(@PathVariable Long userId) {
        Subscription s = subscriptionService.getLatestSubscription(userId);
        SubscriptionStatusDto dto = new SubscriptionStatusDto();
        dto.setUserId(userId);
        if (s == null) {
            dto.setActive(false);
            dto.setStatus(null);
            dto.setExpiresAt(null);
        } else {
            boolean active = s.getStatus() == SubscriptionStatus.ACTIVE && s.getExpiresAt().isAfter(java.time.Instant.now());
            dto.setActive(active);
            dto.setStatus(s.getStatus());
            dto.setExpiresAt(s.getExpiresAt().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cancel/{userId}")
    public ResponseEntity<Boolean> cancel(@PathVariable Long userId) {
        boolean ok = subscriptionService.cancelSubscription(userId);
        return ResponseEntity.ok(ok);
    }
}
