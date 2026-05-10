package com.example.airbnb_clone.service;

import com.example.airbnb_clone.response.GenericResponse;
import com.stripe.model.Event;

public interface PaymentService {
    GenericResponse<?> initiatePayment(Long bookingId, Long userId);
    void capturePayment(Event event);
}
