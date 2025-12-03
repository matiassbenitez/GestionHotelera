package com.example.GestionHotelera.DTO;
import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class datosParaReservaDTO {
  private Integer numeroHabitacion;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private String nombre;
  private String apellido;
  private String telefono;
}
