package com.example.GestionHotelera.repository;

import com.example.GestionHotelera.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface HuespedDAO extends JpaRepository<Huesped, Long> {
  Optional<Huesped> findByNroDocumento(int nroDocumento);
}
