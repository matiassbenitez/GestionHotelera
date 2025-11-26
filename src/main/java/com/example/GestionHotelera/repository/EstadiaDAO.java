package com.example.GestionHotelera.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.GestionHotelera.model.Estadia;

@Repository
public interface EstadiaDAO extends JpaRepository<Estadia, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END
        FROM Estadia e
        WHERE e.habitacion.id = :idHab
          AND :fecha BETWEEN e.fechaEntrada AND e.fechaSalida
    """)
    boolean existeEstadiaActivaEnFecha(
            @Param("idHab") Long idHab,
            @Param("fecha") LocalDate fecha
    );
}

