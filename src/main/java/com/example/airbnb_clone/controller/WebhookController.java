package com.example.airbnb_clone.controller;

import com.example.airbnb_clone.service.PaymentService;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class WebhookController {
    private final PaymentService paymentService;

    ResponseEntity<?> capturePayment(@RequestBody String payload, @RequestHeader("signatureHeader") String sigHeader){
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, "abc");
            paymentService.capturePayment(event);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
