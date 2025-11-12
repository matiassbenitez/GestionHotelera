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
  private final DireccionDAO direccionDAO;
  //private final ContactoDAO contactoDAO;
  //private final UbicacionDAO ubicacionDAO;
 // private final DepartamentoDAO departamentoDAO;
  //private final PosicionFiscalDAO posicionFiscalDAO;

  public GestionHuesped(HuespedDAO huespedDAO, DireccionDAO direccionDAO) {
    this.huespedDAO = huespedDAO;
    this.direccionDAO = direccionDAO;
  }

  public List<Huesped> listarHuespedes() {
    return huespedDAO.findAll();
  }

  public java.util.Optional<Huesped> buscarPorNroDocumento(int nroDocumento) {
    return huespedDAO.findByNroDocumento(nroDocumento);
  }

  public Huesped darDeAltaHuesped(Huesped huesped) {
   
    
    if (huesped.getDireccion() != null) {
      Direccion direccionGuardada = direccionDAO.save(huesped.getDireccion());
      huesped.setDireccion(direccionGuardada);
    }
    return huespedDAO.save(huesped);
  }


}
