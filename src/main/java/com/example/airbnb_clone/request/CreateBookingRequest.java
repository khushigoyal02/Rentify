package com.example.airbnb_clone.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateBookingRequest {
    @NotNull(message = "Property Id is required")
    private Long propertyId;
    @NotNull(message = "Check In date is required")
    private LocalDateTime checkIn;
    @NotNull(message = "Check Out date is required")
    private LocalDateTime checkOut;
    @NotNull(message = "Number of adults is required")
    private Integer adults;
    @NotNull(message = "Number of children is required")
    private Integer children;
    @NotNull(message = "Number of infants")
    private Integer infants;
    @NotNull(message = "Number of pets is required")
    private Integer pets;
}
