package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.PropertyAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyAmenityRepo extends JpaRepository<PropertyAmenity,Long> {
}
