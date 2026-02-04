package com.rishbootdev.subscriptionservice.controller;

import com.rishbootdev.subscriptionservice.dto.CreateOrderRequest;
import com.rishbootdev.subscriptionservice.dto.VerifyPaymentRequest;
import com.rishbootdev.subscriptionservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) throws Exception {
        JSONObject order = paymentService.createOrder(request);
        return ResponseEntity.ok(order.toString());
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPayment(@RequestBody VerifyPaymentRequest request) throws Exception {
        return ResponseEntity.ok(paymentService.verifyPayment(request.getRazorpayOrderId(),request.getRazorpayPaymentId()
                ,request.getRazorpaySignature()));
    }
}

