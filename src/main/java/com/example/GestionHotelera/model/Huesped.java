package com.example.GestionHotelera.model;

import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Huesped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private int nroDocumento;
  private Date fechaNac;
  private String nacionalidad;
  @ManyToOne
  @JoinColumn(name = "direccion_id")
  private Direccion direccion;
  @OneToOne(mappedBy = "huesped")
  private PosicionFiscal posicionFiscal;
  @OneToOne(mappedBy = "huesped")
  private Ubicacion ubicacion;
  @OneToOne(mappedBy = "huesped")
  private Contacto contacto;
}
