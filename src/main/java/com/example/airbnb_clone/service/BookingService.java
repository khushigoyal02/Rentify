package com.example.airbnb_clone.service;

import com.example.airbnb_clone.request.CreateBookingRequest;
import com.example.airbnb_clone.response.GenericResponse;

public interface BookingService {
    GenericResponse<?> initializeBooking(CreateBookingRequest request, Long userId);
    GenericResponse<?> export(Long propertyId);
}
