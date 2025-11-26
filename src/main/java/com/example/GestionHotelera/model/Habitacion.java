package com.example.GestionHotelera.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int numero;

  @Enumerated(EnumType.STRING)
  private TipoHabitacion tipo;

  @OneToOne(optional = false)
  @JoinColumn(name = "estado_id", referencedColumnName = "id")
  private Estado estado;

  @OneToMany(mappedBy = "habitacion")
  private List<Reserva> reserva;

  @OneToMany(mappedBy = "habitacion")
  private List<Estadia> estadia;

  @OneToMany(mappedBy = "habitacion")
  private List<Huesped> huesped;
}
