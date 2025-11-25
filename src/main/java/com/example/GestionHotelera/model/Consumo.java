package com.example.GestionHotelera.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consumo {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int codigo;
  private String tipoConsumo;
  private int cantidad;
  private Float precioUnidad;

  @ManyToOne(optional = false)
  @JoinColumn(name = "estadia_id", referencedColumnName = "id")
  private Estadia estadia;
  
}
