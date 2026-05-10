package com.example.airbnb_clone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "amenity_subcategories")
public class Amenity {
    @Id
    private Long id;

    @ManyToOne
    private AmenityCategory amenityCategory;

    private String name;
    private String description;
}
