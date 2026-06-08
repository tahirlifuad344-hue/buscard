package com.example.buscard.repository;

import com.example.buscard.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository <Trip, Long> {
    Trip findByRouteName(String routeName);
    List<Trip> findAll();
}
