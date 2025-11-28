package com.example.GestionHotelera.DTO;

import java.time.LocalDate;
import java.util.Map;

public class TablaEstadoDTO {
  private LocalDate fecha;
  private Map<Long, String> estadosPorHabitacion;
  public TablaEstadoDTO(LocalDate fecha, Map<Long, String> estadosPorHabitacion) {
    this.fecha = fecha;
    this.estadosPorHabitacion = estadosPorHabitacion;
  }
  public LocalDate getFecha() {
    return fecha;
  }
  public Map<Long, String> getEstadosPorHabitacion() {
    return estadosPorHabitacion;
  }
}
