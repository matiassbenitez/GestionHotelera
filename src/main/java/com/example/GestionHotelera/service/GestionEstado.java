package com.example.GestionHotelera.service;
import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.model.Estado;
import com.example.GestionHotelera.repository.EstadoDAO;

@Service
public class GestionEstado {
  private final EstadoDAO estadoDAOImpl;
  public GestionEstado(EstadoDAO estadoDAOImpl) {
    this.estadoDAOImpl = estadoDAOImpl;
  }
  public List<Estado> obtenerEstadosPorFecha(Date fechaInicio, Date fechaFin) {
    return estadoDAOImpl.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(fechaInicio, fechaFin);
  }
  public List<TablaEstadoDTO> generarTablaEstados(Date fechaInicio, Date fechaFin) {
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    for (Date fecha = fechaInicio; !fecha.after(fechaFin); fecha.setDate(fecha.getDate() + 1)) {
      List<Estado> estadosDelDia = obtenerEstadosPorFecha(fecha, fecha);
      Map<Long, String> estadosPorHabitacion = new HashMap<>();
      for (Estado estado : estadosDelDia) {
        estadosPorHabitacion.put(estado.getHabitacion().getId(), estado.getEstado().name());
      }
      TablaEstadoDTO tablaEstadoDTO = new TablaEstadoDTO(fecha, estadosPorHabitacion);
      tablaEstados.add(tablaEstadoDTO);
    }
    System.out.println(tablaEstados);
    return tablaEstados;
  }
}
