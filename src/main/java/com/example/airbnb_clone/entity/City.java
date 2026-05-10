package com.example.airbnb_clone.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cities")
public class City {
    @Id
    private Long id;

    @ManyToOne
    private Country country;
    private String name;

    private BigDecimal latitude;
    private BigDecimal longitude;
}
