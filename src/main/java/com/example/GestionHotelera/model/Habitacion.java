package com.example.GestionHotelera.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estado> estados = new ArrayList<>();

  @OneToMany(mappedBy = "habitacion")
  private List<Reserva> reserva;

  @OneToMany(mappedBy = "habitacion")
  private List<Estadia> estadia;

  @OneToMany(mappedBy = "habitacion")
  private List<Huesped> huesped;
}
