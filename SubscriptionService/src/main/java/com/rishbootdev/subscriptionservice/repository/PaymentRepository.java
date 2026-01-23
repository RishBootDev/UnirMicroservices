package com.rishbootdev.subscriptionservice.repository;

import com.rishbootdev.subscriptionservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
