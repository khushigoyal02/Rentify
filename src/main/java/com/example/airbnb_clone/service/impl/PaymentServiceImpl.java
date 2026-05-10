package com.example.airbnb_clone.service.impl;

import com.example.airbnb_clone.entity.Booking;
import com.example.airbnb_clone.entity.Payment;
import com.example.airbnb_clone.repo.BookingRepository;
import com.example.airbnb_clone.repo.InventoryRepo;
import com.example.airbnb_clone.repo.PaymentRepo;
import com.example.airbnb_clone.response.GenericResponse;
import com.example.airbnb_clone.service.PaymentService;
import com.example.airbnb_clone.utils.ConstantManager;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepo paymentRepo;
    private final InventoryRepo inventoryRepo;

    public GenericResponse<?> initiatePayment(Long bookingId, Long userId){
        Booking booking=bookingRepository.findById(bookingId).
                orElseThrow(()-> new IllegalArgumentException("Booking not found"));

        // check if current user == booking.guestId
        if (booking.getGuestId()!=userId){
            throw new IllegalArgumentException("Booking does not belong to userId : "+userId);
        }

        String sessionUrl=createCheckoutSession(booking, "http://localhost:8080/success.html", "http://localhost:8080/failure.html");
        return GenericResponse.success(sessionUrl);
    }

    String createCheckoutSession(Booking booking, String successUrl, String failureUrl) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl).setCancelUrl(failureUrl)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("inr")
                                    .setUnitAmount(booking.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValue())
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Booking Payment")
                                            .setDescription("Booking Id: " + booking.getId())
                                            .build()
                                    ).build()
                            ).build()
                    ).build();

            Session session = Session.create(params);

            Payment payment = Payment.builder().bookingId(booking.getId()).paymentSessionId(session.getId())
                    .amount(booking.getTotalPrice()).statusId(ConstantManager.PAYMENT_INITIATED).build();
            paymentRepo.save(payment);
            return session.getUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void capturePayment(Event event){
        if ("checkout.session.completed".equals(event.getType())){
            Session session= (Session) event.getDataObjectDeserializer().getObject().orElse(null);

            Payment payment=paymentRepo.findByPaymentSessionId(session.getId())
                    .orElseThrow(()-> new IllegalArgumentException("Payment session not found"));
            Booking booking=bookingRepository.findById(payment.getBookingId())
                            .orElseThrow(()-> new IllegalArgumentException("Booking not found for this payment session"));


            payment.setStatusId(ConstantManager.PAYMENT_SUCCESS);
            // function to lock inventory to mark reserved -> booked
            inventoryRepo.bookInventory(booking.getPropertyId(), booking.getCheckIn(), booking.getCheckOut());
            booking.setStatusId(ConstantManager.BOOKING_CONFIRMED);

            paymentRepo.save(payment);
            bookingRepository.save(booking);

        } else {
            log.info("Unhandled event {}",event.getType());
        }
    }
}

