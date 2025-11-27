package com.example.GestionHotelera.DTO;

import java.sql.Date;
import java.util.Map;

public class TablaEstadoDTO {
  private Date fecha;
  private Map<Long, String> estadosPorHabitacion;
  public TablaEstadoDTO(Date fecha, Map<Long, String> estadosPorHabitacion) {
    this.fecha = fecha;
    this.estadosPorHabitacion = estadosPorHabitacion;
  }
  public Date getFecha() {
    return fecha;
  }
  public Map<Long, String> getEstadosPorHabitacion() {
    return estadosPorHabitacion;
  }
}
