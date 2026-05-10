package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

    Optional<Payment> findByPaymentSessionId(String id);

    @Query(name = "SELECT * FROM bookings WHERE booking_id=:bookingId " +
            "ORDER BY id DESC LIMIT 1")
    Optional<Payment> findByBookingId(Long bookingId);
}
