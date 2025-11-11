package com.example.GestionHotelera.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionHotelera.model.Departamento;

public interface DepartamentoDAO extends JpaRepository<Departamento, Long> {

}
