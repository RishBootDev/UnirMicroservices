package com.rishbootdev.subscriptionservice.dto;


import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long userId;
    private Double amount;
}

