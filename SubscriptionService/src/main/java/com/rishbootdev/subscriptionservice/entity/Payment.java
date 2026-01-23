package com.rishbootdev.subscriptionservice.entity;

import com.rishbootdev.subscriptionservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    private String orderId;

    private String paymentId;
    private String signature;
    private Long userId;
    private Long amountInPaise;
    private String currency;

    @Enumerated
    private PaymentStatus status;
    private Instant createdAt;
    private Instant verifiedAt;
}
