package com.example.airbnb_clone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BookingStatus {
    @Id
    private Integer id;

    private String name;
    private String description;
}
