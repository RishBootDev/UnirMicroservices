package com.rishbootdev.subscriptionservice.service;


import com.rishbootdev.subscriptionservice.dto.CreateOrderRequest;
import org.json.JSONObject;

public interface PaymentService {
    JSONObject createOrder(CreateOrderRequest request) throws Exception;
    boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) throws Exception;
}

