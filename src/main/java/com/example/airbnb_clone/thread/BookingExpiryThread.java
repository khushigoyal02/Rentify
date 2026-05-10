package com.example.airbnb_clone.thread;

import com.example.airbnb_clone.entity.Booking;
import com.example.airbnb_clone.entity.Payment;
import com.example.airbnb_clone.repo.BookingRepository;
import com.example.airbnb_clone.repo.PaymentRepo;
import com.example.airbnb_clone.utils.ConstantManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BookingExpiryThread implements Runnable{
    private final BookingRepository bookingRepository;
    private final PaymentRepo paymentRepo;

    @Value("${booking.pending.expiry}")
    Long bookingPendingExpiry;

    @Value("${payment.initiated.failed}")
    Long paymentInitiatedFailed;

    @Override
    public void run() {
        List<Booking> pendingBookings=bookingRepository.findByStatusId(ConstantManager.BOOKING_PENDING);

        for (Booking booking : pendingBookings){
            // release inventory
            Optional<Payment> optionalPayment=paymentRepo.findByBookingId(booking.getId());
            if (optionalPayment.isEmpty()){
                Duration duration=Duration.between(booking.getCreatedAt(), LocalDateTime.now());
                long minutes= duration.toMinutes();
                if (minutes>bookingPendingExpiry){
                    booking.setStatusId(ConstantManager.BOOKING_EXPIRED);
                    bookingRepository.save(booking);
                }
            } else {
                Payment payment=optionalPayment.get();
                Duration duration=Duration.between(payment.getCreatedAt(), LocalDateTime.now());
                long minutes=duration.toMinutes();
                if (minutes>paymentInitiatedFailed){
                    payment.setStatusId(ConstantManager.PAYMENT_FAILED);
                    booking.setStatusId(ConstantManager.BOOKING_EXPIRED);
                    paymentRepo.save(payment);
                    bookingRepository.save(booking);
                }
            }
        }
    }
}
