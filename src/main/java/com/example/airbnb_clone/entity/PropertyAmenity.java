package com.example.airbnb_clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "property_amenity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyAmenity {

    @EmbeddedId
    private PropertyAmenityId id;

    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "property_id")
    private Property property;
}

