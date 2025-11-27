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
  private final EstadoDAO estadoDAOImpl;

  public GestionHabitacion(HabitacionDAO habitacionDAOImpl, EstadoDAO estadoDAOImpl) {
    this.habitacionDAOImpl = habitacionDAOImpl;
    this.estadoDAOImpl = estadoDAOImpl;
  }

  public List<Habitacion> obtenerHabitacionPorTipo(TipoHabitacion tipo) {
    return habitacionDAOImpl.findByTipo(tipo);
  }

  
}
