package com.example.GestionHotelera.model;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Factura {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int numero;
  private Date fecha;
  private Float total;

  @OneToOne(optional = false)
  @JoinColumn(name = "estadia_id", referencedColumnName = "id")
  private Estadia estadia;

  @ManyToOne(optional = false)
  private ResponsablePago responsablePago;

  @ManyToOne(optional = true)
  @JoinColumn(name = "nota_de_credito_id", referencedColumnName = "id")
  private NotaDeCredito notaDeCredito;

  @OneToOne(optional = true)
  @JoinColumn(name = "pago_id", referencedColumnName = "id")
  private Pago pago;
}
