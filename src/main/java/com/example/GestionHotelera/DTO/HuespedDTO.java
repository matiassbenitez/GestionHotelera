package com.example.GestionHotelera.DTO;

import java.sql.Date;

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
public class HuespedDTO {
  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private Integer nroDocumento;
  private Date fechaNac;
  private String nacionalidad;
  private String calle;
  private int numero;
  private String departamento;
  private int piso;
  private String localidad;
  private String provincia;
  private String pais;
  private String codPostal;
  private String prefijo;
  private String telefono;
  private String email;
  private String ocupacion;
  private String posicionIva;
  private String cuit;


}
