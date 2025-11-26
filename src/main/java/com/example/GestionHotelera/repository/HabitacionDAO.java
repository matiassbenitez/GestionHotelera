package com.example.GestionHotelera.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.TipoHabitacion;

@Repository
public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByTipo(TipoHabitacion tipo);

    @Query("""
            SELECT h 
            FROM Habitacion h
            """)
    List<Habitacion> findAll();

    @Query("""
        SELECT h
        FROM Habitacion h
        """)
    List<Habitacion> obtenerEstadoHabitaciones(LocalDate desde, LocalDate hasta);
}
