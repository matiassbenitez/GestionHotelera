package com.example.GestionHotelera.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("HUESPED") // <-- Añadir
@Getter
@Setter
@NoArgsConstructor // <-- Añadir (JPA lo necesita)
public class HuespedResponsable extends ResponsablePago {
  
  @OneToOne
  private Huesped huesped;

  public HuespedResponsable(Huesped huesped) {
    this.huesped = huesped;
  }

  @Override
  public String getRazonSocial() {
    return huesped.getNombre() + " " + huesped.getApellido();
  }

  @Override
  public String getDireccion() {
    return huesped.getDireccion().toString();
  }

  @Override
  public String getTelefono() {
    return huesped.getContacto().getTelefono();
  }

  @Override
  public String getCuit() {
    return huesped.getPosicionFiscal().getCuit();
  }
}
