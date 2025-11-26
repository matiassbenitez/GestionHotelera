package com.example.GestionHotelera.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.GestionHotelera.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reserva r
        WHERE r.habitacion.id = :idHab
          AND :fecha BETWEEN r.fechaEntrada AND r.fechaSalida
    """)
    boolean existeReservaEnFecha(
            @Param("idHab") Long idHab,
            @Param("fecha") LocalDate fecha
    );
}
