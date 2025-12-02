package com.example.GestionHotelera.DTO;

import java.time.LocalDate;
import java.util.Map;

public class TablaEstadoDTO {
  private LocalDate fecha;
  private Map<Integer, String> estadosPorHabitacion;
  public TablaEstadoDTO(LocalDate fecha, Map<Integer, String> estadosPorHabitacion) {
    this.fecha = fecha;
    this.estadosPorHabitacion = estadosPorHabitacion;
  }
  public LocalDate getFecha() {
    return fecha;
  }
  public Map<Integer, String> getEstadosPorHabitacion() {
    return estadosPorHabitacion;
  }
}
