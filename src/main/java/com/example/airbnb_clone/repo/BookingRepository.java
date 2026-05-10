package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByPropertyId(Long propertyId);
    List<Booking> findByStatusId(Integer statusId);
}
