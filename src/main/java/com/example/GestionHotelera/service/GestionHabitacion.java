package com.example.GestionHotelera.service;
import com.example.GestionHotelera.repository.HabitacionDAO;
import com.example.GestionHotelera.repository.EstadoDAO;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.TipoHabitacion;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GestionHabitacion {
  
  private final HabitacionDAO habitacionDAOImpl;

  public GestionHabitacion(HabitacionDAO habitacionDAOImpl, EstadoDAO estadoDAOImpl) {
    this.habitacionDAOImpl = habitacionDAOImpl;
  }

  public List<Habitacion> obtenerHabitacionesPorTipo(TipoHabitacion tipoHabitacion) {
    return habitacionDAOImpl.findByTipo(tipoHabitacion);
  }

  public Habitacion buscarPorNumero(int numero) {
    return habitacionDAOImpl.findByNumero(numero);
  }

  
}
