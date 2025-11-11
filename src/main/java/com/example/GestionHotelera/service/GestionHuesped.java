package com.example.GestionHotelera.service;

import java.util.List;

import com.example.GestionHotelera.model.Contacto;
import com.example.GestionHotelera.model.Departamento;
import com.example.GestionHotelera.model.Direccion;
import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.model.PosicionFiscal;
import com.example.GestionHotelera.model.Ubicacion;
import com.example.GestionHotelera.repository.HuespedDAO;
import com.example.GestionHotelera.repository.ContactoDAO;
import com.example.GestionHotelera.repository.UbicacionDAO;
import com.example.GestionHotelera.repository.DireccionDAO;
import com.example.GestionHotelera.repository.DepartamentoDAO;
import com.example.GestionHotelera.repository.PosicionFiscalDAO;


public class GestionHuesped {
  
  private final HuespedDAO huespedDAO;
  private final ContactoDAO contactoDAO;
  private final UbicacionDAO ubicacionDAO;
  private final DepartamentoDAO departamentoDAO;
  private final PosicionFiscalDAO posicionFiscalDAO;
  private final DireccionDAO direccionDAO;
  public GestionHuesped(HuespedDAO huespedDAO, ContactoDAO contactoDAO, UbicacionDAO ubicacionDAO,
      DepartamentoDAO departamentoDAO, PosicionFiscalDAO posicionFiscalDAO, DireccionDAO direccionDAO) {
    this.huespedDAO = huespedDAO;
    this.contactoDAO = contactoDAO;
    this.ubicacionDAO = ubicacionDAO;
    this.departamentoDAO = departamentoDAO;
    this.posicionFiscalDAO = posicionFiscalDAO;
    this.direccionDAO = direccionDAO;
  }

  public List<Huesped> listarHuespedes() {
    return huespedDAO.findAll();
  }
  public Huesped darDeAltaHuesped(Huesped huesped) {
    if (huesped.getContacto() != null) {
      Contacto contactoGuardado = contactoDAO.save(huesped.getContacto());
      huesped.setContacto(contactoGuardado);
    }
    if (huesped.getUbicacion() != null) {
      Ubicacion ubicacionGuardada = ubicacionDAO.save(huesped.getUbicacion());
      huesped.setUbicacion(ubicacionGuardada);
    }
    if (huesped.getPosicionFiscal() != null) {
      PosicionFiscal posicionFiscalGuardada = posicionFiscalDAO.save(huesped.getPosicionFiscal());
      huesped.setPosicionFiscal(posicionFiscalGuardada);
    }
    if (huesped.getDireccion() != null) {
      Direccion direccionGuardada = direccionDAO.save(huesped.getDireccion());
      huesped.setDireccion(direccionGuardada);
      if (huesped.getDireccion().getDepartamento() != null) {
        Departamento departamentoGuardado = departamentoDAO.save(huesped.getDireccion().getDepartamento());
        huesped.getDireccion().setDepartamento(departamentoGuardado);
      }
    }
    return huespedDAO.save(huesped);
  }


}
