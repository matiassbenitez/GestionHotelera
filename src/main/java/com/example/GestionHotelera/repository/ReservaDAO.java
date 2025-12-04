package com.example.GestionHotelera.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long> {
  List<Reserva> findByHabitacionAndIngresoLessThanEqualAndEgresoGreaterThanEqual(
        Habitacion habitacion, 
        LocalDate fechaFin, // Corresponde al ingreso de la reserva
        LocalDate fechaInicio // Corresponde al egreso de la reserva
    );
}
