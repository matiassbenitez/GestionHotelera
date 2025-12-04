package com.example.GestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionHotelera.model.Estado;

import java.time.LocalDate;
import java.util.List;


public interface EstadoDAO extends JpaRepository<Estado, Long> {
    public List<Estado> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndEliminadoFalse(LocalDate fecha, LocalDate fecha2);
    public List<Estado> findByHabitacion_NumeroAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndEliminadoFalse(int numero, LocalDate fecha, LocalDate fecha2);
}
