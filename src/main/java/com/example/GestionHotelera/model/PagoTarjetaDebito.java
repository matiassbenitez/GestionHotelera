package com.example.GestionHotelera.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PagoTarjetaDebito extends Pago {

  private String tarjetaDebito;

}