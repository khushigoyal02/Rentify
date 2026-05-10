package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.Property;
import com.example.airbnb_clone.request.PropertyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query(value = "select p.title from properties p left join bookings b on p.id=b.property_id " +
            "where p.city_id = :cityId and (:startDate<b.check_in and :endDate<b.check_in)  or :startDate>b.check_out "
            + "and p.status_id=6002", nativeQuery = true)
    List<PropertyProjection> search(Long cityId, LocalDateTime startDate, LocalDateTime endDate);

    List<Property> findAllByHostId(Long hostId);
}
