package com.example.GestionHotelera.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Getter
@Setter
@ToString(exclude = {"direccion", "posicionFiscal", "ubicacion", "contacto", "habitacion"})
public class Huesped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private String nroDocumento;
  private Date fechaNac;
  private String nacionalidad;
  
  @ManyToOne
  @JoinColumn(name = "direccion_id")
  private Direccion direccion;

  @OneToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "posicion_fiscal_id")
  private PosicionFiscal posicionFiscal;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ubicacion_id")
  private Ubicacion ubicacion;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contacto_id")
  private Contacto contacto;

  @ManyToOne(optional = true)
  @JoinColumn(name = "habitacion_id", referencedColumnName = "id")
  private Habitacion habitacion;

}
