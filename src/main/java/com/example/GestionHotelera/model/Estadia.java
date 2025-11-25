package com.example.GestionHotelera.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estadia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Float valor;
  @OneToOne(optional = true)
  @JoinColumn(name = "reserva_id", referencedColumnName = "id")
  private Reserva reserva;

  @OneToMany(mappedBy = "estadia")
  private List<Consumo> consumo;

  @OneToOne(mappedBy = "estadia")
  private Factura factura;
  
  @ManyToOne(optional = false)
  @JoinColumn(name = "habitacion_id", referencedColumnName = "id")
  private Habitacion habitacion;
}
