package com.example.GestionHotelera.service;

import java.util.List;
import com.example.GestionHotelera.repository.DireccionDAO;
import com.example.GestionHotelera.model.Direccion;

public class GestionDireccion {
  private final DireccionDAO direccionDAO;

  public GestionDireccion(DireccionDAO direccionDAO) {
    this.direccionDAO = direccionDAO;
  }

  public List<Direccion> listarDirecciones() {
    return direccionDAO.findAll();
  }

  public Direccion guardarDireccion(Direccion direccion) {
    return direccionDAO.save(direccion);
  }
}
