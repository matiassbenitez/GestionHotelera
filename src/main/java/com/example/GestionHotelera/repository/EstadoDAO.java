package com.example.GestionHotelera.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionHotelera.model.Estado;

public interface EstadoDAO extends JpaRepository<Estado, Long> {
  
}
