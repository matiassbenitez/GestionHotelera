package com.example.GestionHotelera.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PagoTarjetaCredito extends Pago {

  private String tarjetaCredito;

}
