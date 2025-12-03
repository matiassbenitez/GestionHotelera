package com.example.GestionHotelera.service;
import org.springframework.stereotype.Service;

import com.example.GestionHotelera.DTO.datosParaReservaDTO;
import com.example.GestionHotelera.model.Reserva;
import com.example.GestionHotelera.repository.ReservaDAO;

@Service
public class GestionReserva {
  ReservaDAO reservaDAOImpl;
  public GestionReserva(ReservaDAO reservaDAOImpl) {
    this.reservaDAOImpl = reservaDAOImpl;
  }
  public void reservar(datosParaReservaDTO datos) {
    Reserva reserva = new Reserva();
    reserva.setNombre(datos.getNombre());
    reserva.setApellido(datos.getApellido());
    reserva.setTelefono(datos.getTelefono());
    reservaDAOImpl.save(reserva); 
  }
}
