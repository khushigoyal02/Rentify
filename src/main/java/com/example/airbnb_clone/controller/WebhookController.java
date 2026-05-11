package com.example.airbnb_clone.controller;

import com.example.airbnb_clone.service.PaymentService;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
    @Value("${stripe.webhook.secret}")
    String endpointSecret;

    private final PaymentService paymentService;

    @PostMapping("/payment")
    ResponseEntity<?> capturePayment(@RequestBody String payload, @RequestHeader("signatureHeader") String sigHeader){
        log.info("Webhook called");
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            paymentService.capturePayment(event);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            throw new RuntimeException("Webhook call failed");
        }
    }
}
