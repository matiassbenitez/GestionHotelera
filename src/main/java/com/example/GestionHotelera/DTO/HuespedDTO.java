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
  private String apellido;
  private String nombres;
  private Date fechaNacimiento;
  private String tipoDocumento;
  private Integer nroDocumento;
  private String nacionalidad;
  private String calle;
  private String nroCalle;
  private String departamento;
  private Integer piso;
  private String pais;
  private String provincia;
  private String localidad;
  private Integer codigoPostal;
  private String prefijo;
  private String telefono;
  private String email;
  private String ocupacion;
  private String posicionIva;
  private String cuit;


}
