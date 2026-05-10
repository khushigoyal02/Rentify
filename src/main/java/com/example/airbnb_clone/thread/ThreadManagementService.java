package com.example.airbnb_clone.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
@Component
public class ThreadManagementService {

    @Value("${run.booking.expiry}")
    boolean bookingExpiry;

    @Value("${run.inventory.generation}")
    boolean inventoryGeneration;

    private final BookingExpiryThread bookingExpiryThread;
    private final InventoryGenerationThread inventoryGenerationThread;

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void runBookingExpiryThread(){
        if (bookingExpiry)
            bookingExpiryThread.run();
    }

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void runInventoryGenerationThread(){
        if (inventoryGeneration)
            inventoryGenerationThread.run();
    }
}
