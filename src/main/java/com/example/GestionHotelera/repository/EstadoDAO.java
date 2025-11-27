package com.example.GestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionHotelera.model.Estado;

import java.sql.Date;
import java.util.List;


public interface EstadoDAO extends JpaRepository<Estado, Long> {
   public List<Estado> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(Date fecha, Date fecha2);
}
