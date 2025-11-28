package com.example.GestionHotelera.service;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.model.Estado;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.repository.EstadoDAO;

@Service
public class GestionEstado {
  private final EstadoDAO estadoDAOImpl;
  public GestionEstado(EstadoDAO estadoDAOImpl) {
    this.estadoDAOImpl = estadoDAOImpl;
  }
  public List<Estado> obtenerEstadosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
    return estadoDAOImpl.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(fechaInicio, fechaFin);
  }
  public List<TablaEstadoDTO> generarTablaEstados(LocalDate fechaInicio, LocalDate fechaFin, List<Habitacion> habitaciones) {
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    List<Long> idsHabitaciones = new ArrayList<>();
    habitaciones.forEach(h -> idsHabitaciones.add(h.getId()));
    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha=fecha.plusDays(1)) {
      List<Estado> estadosDelDia = obtenerEstadosPorFecha(fecha, fecha);
      Map<Long, String> estadosPorHabitacion = new TreeMap<>();
      System.out.println("idsHabitaciones: " + idsHabitaciones.toString());
      for (Estado estado : estadosDelDia) {
        System.out.println("Estado encontrado: Habitacion ID " + estado.getHabitacion().getId() + " - Estado: " + estado.getEstado());
        if (idsHabitaciones.contains(estado.getHabitacion().getId())) {
          estadosPorHabitacion.put(estado.getHabitacion().getId(), estado.getEstado().toString());
        }
        System.out.println("keys en el mapa: " + estadosPorHabitacion.keySet());
        List<Long> habitacionesFaltantes = new ArrayList<>(idsHabitaciones);
        habitacionesFaltantes.removeAll(estadosPorHabitacion.keySet());
        for (Long idFaltante : habitacionesFaltantes) {
          estadosPorHabitacion.put(idFaltante, "LIBRE");
        }
      }
      System.out.println("Fecha: " + fecha + " - Estados: " + estadosPorHabitacion);
      TablaEstadoDTO tablaEstadoDTO = new TablaEstadoDTO(fecha, estadosPorHabitacion);
      tablaEstados.add(tablaEstadoDTO);
    }
    System.out.println("cantidad de estados: " + tablaEstados.size());
    return tablaEstados;
  }
}
