package com.example.airbnb_clone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "property_type")
public class PropertyType {
    @Id
    private Long id;

    private String name;
    private String description;
}
