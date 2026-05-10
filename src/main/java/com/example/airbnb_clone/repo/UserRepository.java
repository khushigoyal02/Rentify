package com.example.airbnb_clone.repo;

import com.example.airbnb_clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
