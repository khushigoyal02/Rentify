package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
}
