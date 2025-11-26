package com.example.GestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionHotelera.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long> {

   
}
