package com.example.GestionHotelera.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Ubicacion {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private String pais;
  private String provincia;
  private String localidad;
  private String codPostal;
  @OneToOne(mappedBy = "ubicacion")
  private Huesped huesped;
}
