package com.example.GestionHotelera.service;
import com.example.GestionHotelera.repository.HabitacionDAO;
import com.example.GestionHotelera.repository.EstadoDAO;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.model.TipoHabitacion;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GestionHabitacion {
  
  private final HabitacionDAO habitacionDAOImpl;
  private final GestionHuesped gestionHuesped;
  private final GestionEstado gestionEstado;

  public GestionHabitacion(HabitacionDAO habitacionDAOImpl, EstadoDAO estadoDAOImpl, GestionHabitacion gestionHabitacion, GestionHuesped gestionHuesped, GestionEstado gestionEstado) {
    this.habitacionDAOImpl = habitacionDAOImpl;
    this.gestionHuesped = gestionHuesped;
    this.gestionEstado = gestionEstado;
  }

  List<Habitacion> obtenerHabitacionesPorTipo(TipoHabitacion tipoHabitacion) {
    return habitacionDAOImpl.findByTipo(tipoHabitacion);
  }

  public Habitacion buscarPorNumero(int numero) {
    return habitacionDAOImpl.findByNumero(numero);
  }


  public void procesarOcuparHabitacion (Long idHuesped,
      Integer numeroHabitacion, LocalDate fechaInicio,LocalDate fechaFin){

        Huesped huesped = gestionHuesped.buscarPorId(idHuesped);
        System.out.println("huesped seleccionado: " + huesped.getNombre() + " " + huesped.getApellido());
        Habitacion habitacion = buscarPorNumero(numeroHabitacion);
        System.out.println("habitacion a ocupar: " + habitacion.getNumero());
        huesped.setHabitacion(habitacion);
        gestionHuesped.guardarHuesped(huesped);
        System.out.println("habitacion asignada al huesped: " + huesped.getHabitacion().getNumero());




      }


  public void guardarEstadoHabitacion (Integer numeroHabitacion,
     LocalDate fechaInicio,
     LocalDate fechaFin) {


      Habitacion habitacion = buscarPorNumero(numeroHabitacion);
      gestionEstado.crearEstadoOcupado(habitacion, fechaInicio, fechaFin);


  }



  
}
