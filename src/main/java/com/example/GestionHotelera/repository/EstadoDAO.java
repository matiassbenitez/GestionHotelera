package com.example.GestionHotelera.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.GestionHotelera.model.Estado;


@Repository
public interface EstadoDAO extends JpaRepository<Estado, Long> {
       @Query("""
    SELECT e.estado
    FROM Estado e
    WHERE e.id = :idHab
      AND :fecha BETWEEN fechaInicio AND fechaFin
""")
String EstadoEnFecha(@Param("idHab") Long idHab,
                     @Param("fecha") LocalDate fecha);
}
