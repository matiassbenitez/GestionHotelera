package com.example.GestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionHotelera.model.Habitacion;

public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {
  
}
