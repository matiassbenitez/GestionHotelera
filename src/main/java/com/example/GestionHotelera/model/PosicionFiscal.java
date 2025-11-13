package com.example.GestionHotelera.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class PosicionFiscal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String ocupacion;
  private String posicionIVA;
  private String cuit;
  private boolean responsableDePago = false;
  @OneToOne(mappedBy = "posicionFiscal")
  //@JoinColumn(name = "huesped_id", referencedColumnName = "id")
  private Huesped huesped;
}
