package com.example.airbnb_clone.service.impl;

import com.example.airbnb_clone.entity.*;
import com.example.airbnb_clone.helper.ExcelGenerator;
import com.example.airbnb_clone.repo.*;
import com.example.airbnb_clone.request.CreateBookingRequest;
import com.example.airbnb_clone.response.GenericResponse;
import com.example.airbnb_clone.service.BookingService;
import com.example.airbnb_clone.utils.ConstantManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private final InventoryRepo inventoryRepo;
    private final ExcelGenerator excelGenerator;

    @Transactional
    public GenericResponse<?> initializeBooking(CreateBookingRequest request, Long userId) {
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(()-> new IllegalArgumentException("Property not found"));

        LocalDateTime startDate = request.getCheckIn();
        LocalDateTime endDate = request.getCheckOut();

        // availability check for dates
        // if true, then use lock reserve inventories (10 mins expire handled by scheduler)
        // if reserved, booking is created with status pending

        // if no payment in 10 mins window then status expired and use lock to release reserved inventories (thread)

        // click on pay button, status confirmed and use lock to convert reserved to book
        // (use lock every time working with inventories)

        // background job for bookings pending -> expired
        // background job for payment initiated -> failed

        long totalDays= ChronoUnit.DAYS.between(startDate,endDate)+1;
        int updatedDays= inventoryRepo.reserveInventory(request.getPropertyId(), startDate, endDate.plusDays(1));
        log.info("Total Days: {} Updated Days: {}", totalDays, updatedDays);
        if (updatedDays<totalDays){
            throw new RuntimeException("Inventory not available");
        }

        BigDecimal totalPrice=inventoryRepo.calculateTotalPrice(request.getPropertyId(), startDate, endDate.plusDays(1));
        Booking booking = Booking.builder().checkIn(request.getCheckIn()).checkOut(request.getCheckOut())
                .adultsCount(request.getAdults())
                .childrenCount(request.getChildren()).infantsCount(request.getInfants()).petsCount(request.getPets())
                .guestId(userId).totalPrice(totalPrice).statusId(ConstantManager.BOOKING_PENDING)
                .days((int)totalDays).propertyId(request.getPropertyId()).build();

        bookingRepository.save(booking);
        return GenericResponse.success(null);
    }

    public GenericResponse<?> export(Long propertyId) {
        Map<String,Object> responseMap=new HashMap<>();
        List<Booking> bookings = bookingRepository.findByPropertyId(propertyId);
        ByteArrayInputStream sheet = excelGenerator.generateExcel(bookings);
        log.info(sheet.toString());

        responseMap.put("excelSheet", sheet);
        return GenericResponse.success(responseMap);
    }
}
