package com.example.GestionHotelera.service;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.model.Estado;
import com.example.GestionHotelera.model.EstadoEnum;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.repository.EstadoDAO;

@Service
public class GestionEstado {
  private final EstadoDAO estadoDAOImpl;
  public GestionEstado(EstadoDAO estadoDAOImpl) {
    this.estadoDAOImpl = estadoDAOImpl;
  }
  public void crearEstado(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
    Estado nuevoEstado = new Estado();
    nuevoEstado.setHabitacion(habitacion);
    nuevoEstado.setFechaInicio(fechaInicio);
    nuevoEstado.setFechaFin(fechaFin);
    nuevoEstado.setEstado(EstadoEnum.RESERVADA);
    estadoDAOImpl.save(nuevoEstado);
  }

  public List<Estado> obtenerEstadosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
    //System.out.println("GestionEstado - obtenerEstadosPorFecha: " + fechaInicio + " / " + fechaFin);
    return estadoDAOImpl.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(fechaInicio, fechaFin);
  }
  public List<TablaEstadoDTO> generarTablaEstados(LocalDate fechaInicio, LocalDate fechaFin, List<Habitacion> habitaciones) {
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    List<Integer> nroHabitaciones = new ArrayList<>();
    habitaciones.forEach(h -> nroHabitaciones.add(h.getNumero()));
    for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha=fecha.plusDays(1)) {
      List<Estado> estadosDelDia = obtenerEstadosPorFecha(fecha, fecha);
      System.out.println("Fecha: " + fecha + " - Estados encontrados: " + estadosDelDia.size());
      Map<Integer, String> estadosPorHabitacion = new TreeMap<>();
      //System.out.println("nroHabitaciones: " + nroHabitaciones.toString());
      for (Estado estado : estadosDelDia) {
        //System.out.println("Estado encontrado: Habitacion nro " + estado.getHabitacion().getNumero() + " - Estado: " + estado.getEstado());
        if (nroHabitaciones.contains(estado.getHabitacion().getNumero())) {
          estadosPorHabitacion.put(estado.getHabitacion().getNumero(), estado.getEstado().toString());
        }
      }
        //System.out.println("keys en el mapa: " + estadosPorHabitacion.keySet());
        List<Integer> habitacionesFaltantes = new ArrayList<>(nroHabitaciones);
        System.out.println("habitacionesFaltantes antes de remover: " + habitacionesFaltantes);
        habitacionesFaltantes.removeAll(estadosPorHabitacion.keySet());
        System.out.println("habitacionesFaltantes despues de remover: " + habitacionesFaltantes);
        for (Integer idFaltante : habitacionesFaltantes) {
          estadosPorHabitacion.put(idFaltante, "LIBRE");
        }
      //System.out.println("Fecha: " + fecha + " - Estados: " + estadosPorHabitacion);
      TablaEstadoDTO tablaEstadoDTO = new TablaEstadoDTO(fecha, estadosPorHabitacion);
      tablaEstados.add(tablaEstadoDTO);
    }
    //System.out.println("cantidad de estados: " + tablaEstados.size());
    return tablaEstados;
  }
  
  
}
