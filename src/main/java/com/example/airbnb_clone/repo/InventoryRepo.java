package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<InventoryEntity,Long> {
    @Modifying
    @Query(value = "UPDATE inventories SET reserved_units=reserved_units+1 WHERE " +
            "property_id=:propertyId AND closed=false AND date BETWEEN :startDate AND :endDate " +
            "AND (total_units-reserved_units-booked_units)>=1", nativeQuery = true)
    int reserveInventory(Long propertyId, LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query(value = "UPDATE inventories SET booked_units=booked_units+1 AND reserved_units=reserved_units-1 WHERE " +
            "property_id=:propertyId AND closed=false AND date BETWEEN :startDate AND :endDate " +
            "AND reserved_units>=1", nativeQuery = true)
    void bookInventory(Long propertyId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT SUM(price) FROM inventories WHERE property_id=:propertyId " +
            "AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    BigDecimal calculateTotalPrice(Long propertyId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<InventoryEntity> findByDateAndPropertyId(LocalDateTime date, Long propertyId);
}
