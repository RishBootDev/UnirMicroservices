package com.rishbootdev.subscriptionservice.controller;

import com.rishbootdev.subscriptionservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    @Value("${payment.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(HttpServletRequest request) throws Exception {

        String signatureHeader = request.getHeader("X-Razorpay-Signature");
        String payload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        try {
            com.razorpay.Utils.verifyWebhookSignature(payload, signatureHeader, webhookSecret);

            org.json.JSONObject json = new org.json.JSONObject(payload);
            String event = json.optString("event");
            if ("payment.captured".equals(event) || "payment.authorized".equals(event) || "payment.failed".equals(event)) {
                org.json.JSONObject payloadObj = json.getJSONObject("payload");
                org.json.JSONObject paymentObj = payloadObj.getJSONObject("payment").getJSONObject("entity");
                String razorpayOrderId = paymentObj.optString("order_id");
                String razorpayPaymentId = paymentObj.optString("id");
                String status = paymentObj.optString("status"); // "captured", "authorized", "failed"

                paymentService.handleWebhookPayment(razorpayOrderId, razorpayPaymentId, status);
            }
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("invalid webhook: " + e.getMessage());
        }
    }
}
