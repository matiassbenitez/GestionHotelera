package com.example.GestionHotelera.model;
import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PagoCheque extends Pago {

  private int numero;
  private String banco;
  private String plaza;
  private Date fechaDeCobro;

}
