package com.example.airbnb_clone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_type")
public class RoomType {
    @Id
    private Long id;

    private String name;
    private String description;
}
