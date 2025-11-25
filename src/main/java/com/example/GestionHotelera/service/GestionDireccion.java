package com.example.GestionHotelera.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.GestionHotelera.repository.DireccionDAO;
import com.example.GestionHotelera.model.Direccion;

@Service
public class GestionDireccion {
  private final DireccionDAO direccionDAO;

  public GestionDireccion(DireccionDAO direccionDAO) {
    this.direccionDAO = direccionDAO;
  }

  public List<Direccion> listarDirecciones() {
    return direccionDAO.findAll();
  }

  public Optional<Direccion> buscarDireccion(String calle, int numero, String departamento, String piso) {
    return direccionDAO.findByCalleAndNumeroAndDepartamento_DepartamentoAndDepartamento_Piso(calle, numero, departamento, piso);
  }

  public Direccion guardarDireccion(Direccion direccion) {
    return direccionDAO.save(direccion);
  }
}
