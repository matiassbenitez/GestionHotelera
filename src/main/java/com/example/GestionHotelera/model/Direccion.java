package com.example.GestionHotelera.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"huespedes", "departamento"})
public class Direccion {  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String calle;
  private int numero;

  @OneToMany(mappedBy = "direccion")
  private List<Huesped> huespedes;

  @OneToOne(optional = true,cascade = CascadeType.PERSIST)
  @JoinColumn(name = "departamento_id", referencedColumnName = "id")
  private Departamento departamento;

}
