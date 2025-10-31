package com.example.GestionHotelera.repository;

import com.example.GestionHotelera.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DireccionDAO extends JpaRepository<Direccion, Long> {

}
