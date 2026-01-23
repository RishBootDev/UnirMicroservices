package com.rishbootdev.subscriptionservice.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.rishbootdev.subscriptionservice.dto.CreateOrderRequest;
import com.rishbootdev.subscriptionservice.entity.Payment;
import com.rishbootdev.subscriptionservice.enums.PaymentStatus;
import com.rishbootdev.subscriptionservice.repository.PaymentRepository;
import com.rishbootdev.subscriptionservice.service.PaymentService;
import com.rishbootdev.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final SubscriptionService subscriptionService;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    @Transactional
    public JSONObject createOrder(CreateOrderRequest request) throws Exception {

        JSONObject options = new JSONObject();
        long amountInPaise = Math.round(request.getAmount() * 100);

        options.put("amount", amountInPaise);
        options.put("currency", "INR");
        options.put("receipt", "premium_txn_" + System.currentTimeMillis());
        options.put("payment_capture", 1);

        Order order = razorpayClient.orders.create(options);

        Payment payment = new Payment();
        payment.setOrderId(order.get("id"));
        payment.setUserId(request.getUserId());
        payment.setAmountInPaise(amountInPaise);
        payment.setCurrency("INR");
        payment.setStatus(PaymentStatus.CREATED);
        payment.setCreatedAt(Instant.now());

        paymentRepository.save(payment);

        return order.toJson();
    }

    @Override
    @Transactional
    public boolean verifyPayment(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature
    ) throws Exception {

        JSONObject attributes = new JSONObject();
        attributes.put("razorpay_order_id", razorpayOrderId);
        attributes.put("razorpay_payment_id", razorpayPaymentId);
        attributes.put("razorpay_signature", razorpaySignature);

        boolean valid = Utils.verifyPaymentSignature(attributes, razorpayKeySecret);

        Payment payment = paymentRepository.findById(razorpayOrderId)
                .orElseThrow(() -> new IllegalStateException("Order not found: " + razorpayOrderId));

        if (valid) {
            payment.setPaymentId(razorpayPaymentId);
            payment.setSignature(razorpaySignature);
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setVerifiedAt(Instant.now());
            paymentRepository.save(payment);

            subscriptionService.createOrExtendSubscription(payment.getUserId(), 30);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
        }

        return valid;
    }

}
