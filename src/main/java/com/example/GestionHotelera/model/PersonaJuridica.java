package com.example.GestionHotelera.model;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("JURIDICA")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class PersonaJuridica extends ResponsablePago {

  private String razonSocial;
  private String direccion;
  private String telefono;
  private String cuit;

  @Override
  public String getRazonSocial() {
    return razonSocial;
  }
  @Override
  public String getDireccion() {
    return direccion;
  }
  @Override
  public String getTelefono() {
    return telefono;
  }
  @Override
  public String getCuit() {
    return cuit;
  }

}

