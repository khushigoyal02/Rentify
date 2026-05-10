package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
