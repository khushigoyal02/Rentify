package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepo extends JpaRepository<BookingStatus,Integer> {
}
