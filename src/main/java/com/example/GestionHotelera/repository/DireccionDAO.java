package com.example.GestionHotelera.repository;

import com.example.GestionHotelera.model.Direccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DireccionDAO extends JpaRepository<Direccion, Long> {
  Optional<Direccion> findByCalleAndNumeroAndDepartamento_DepartamentoAndDepartamento_Piso(String calle, int numero, String departamento, int piso);
}
