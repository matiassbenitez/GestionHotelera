package com.example.GestionHotelera.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Direccion {  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String calle;
  private int numero;
  @OneToMany(mappedBy = "direccion", cascade = jakarta.persistence.CascadeType.ALL)
  private List<Huesped> huespedes;
}
