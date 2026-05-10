package com.example.airbnb_clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "properties")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hostId;

    private String title;
    private String description;

    private Long propertyTypeId;
    private Long roomTypeId;

    private String addressLine;
    private Long cityId;

    private Integer maximumGuests;
    private Integer bedroomsCount;
    private Integer bedsCount;
    private Integer bathroomsCount;

    private BigDecimal weekdayBasePrice;
    private BigDecimal weekendBasePrice;

    private String folderPath;

    @Transient
    private List<Long> amenityIds;
    private Integer statusId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}