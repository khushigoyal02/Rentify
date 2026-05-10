package com.example.airbnb_clone.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PropertyAmenityId implements Serializable {

    private Long propertyId;
    private Long amenityId;
}

