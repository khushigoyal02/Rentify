package com.example.airbnb_clone.controller;

import com.example.airbnb_clone.request.CreateBookingRequest;
import com.example.airbnb_clone.request.FetchPropertyRequest;
import com.example.airbnb_clone.service.BookingService;
import com.example.airbnb_clone.service.PaymentService;
import com.example.airbnb_clone.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

    private final PropertyService propertyService;
    private final BookingService bookingService;
    private final PaymentService paymentService;

    @PostMapping("/get-properties")
    ResponseEntity<?> search(@Valid @RequestBody FetchPropertyRequest request) {
        return ResponseEntity.ok(propertyService.searchAllProperties(request));
    }

    @PostMapping("/create-booking")
    ResponseEntity<?> create(@RequestBody CreateBookingRequest request, @RequestHeader Long userId) {
        return ResponseEntity.ok(bookingService.initializeBooking(request, userId));
    }

    @PostMapping("/payment")
    ResponseEntity<?> payment(@RequestParam Long bookingId, @RequestHeader Long userId){
        return ResponseEntity.ok(paymentService.initiatePayment(bookingId, userId));
    }
}
