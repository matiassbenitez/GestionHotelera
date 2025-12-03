package com.example.GestionHotelera.model;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate fechaReserva;
  private String nombre;
  private String apellido;
  private String telefono;
  private Boolean cancelada = false;
  private LocalDate ingreso;
  private LocalDate egreso;

  @OneToOne(mappedBy = "reserva")
  private Estadia estadia;

  @ManyToOne(optional = false)
  @JoinColumn(name = "habitacion_id", referencedColumnName = "id")
  private Habitacion habitacion;
}
