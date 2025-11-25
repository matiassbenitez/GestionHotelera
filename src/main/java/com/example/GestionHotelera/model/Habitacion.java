package com.example.GestionHotelera.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
