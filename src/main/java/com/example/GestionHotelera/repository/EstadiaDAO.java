package com.example.GestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestionHotelera.model.Estadia;

@Repository
public interface EstadiaDAO extends JpaRepository<Estadia, Long> {


}

