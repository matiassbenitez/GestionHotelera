package com.example.GestionHotelera.repository;

import com.example.GestionHotelera.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HuespedDAO extends JpaRepository<Huesped, Long>, JpaSpecificationExecutor<Huesped> {
  Optional<Huesped> findByNroDocumentoAndTipoDocumento(String nroDocumento, String tipoDocumento);
  // List<Huesped> findByApellidoStartingWithIgnoreCaseAndNombreStartingWithIgnoreCaseAndTipoDocumentoAndNroDocumento(
  //     String apellido, String nombre, String tipoDocumento, String nroDocumento);
  // List<Huesped> findByApellidoStartingWithIgnoreCaseAndNombreStartingWithIgnoreCaseAndTipoDocumento(
  //     String apellido, String nombre, String tipoDocumento);
  // List<Huesped> findByApellidoStartingWithIgnoreCaseAndNombreStartingWithIgnoreCaseAndNroDocumento(
  //     String apellido, String nombre, String nroDocumento);
  // List<Huesped> findByApellidoStartingWithIgnoreCaseAndNombreStartingWithIgnoreCase(
  //     String apellido, String nombre);

}
