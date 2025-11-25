package com.example.GestionHotelera.specifications;
import org.springframework.data.jpa.domain.Specification;
import com.example.GestionHotelera.model.Huesped;

public class HuespedSpecification {
  public static Specification<Huesped> apellidoStartsWith(String prefix) {
    return (root, query, builder) -> {
      
      String pattern = prefix.toUpperCase() + "%";
      return builder.like(
        builder.upper(root.get("apellido")), 
        pattern);
    };
  }
  public static Specification<Huesped> nombreStartsWith(String prefix) {
    return (root, query, builder) -> {
      
      String pattern = prefix.toUpperCase() + "%";
      return builder.like(
        builder.upper(root.get("nombre")), 
        pattern);
    };
  }
  public static Specification<Huesped> hasNroDocumento(String nroDocumento) {
    return (root, query, builder) -> 
      builder.equal(root.get("nroDocumento"), nroDocumento);
  }
  public static Specification<Huesped> hasTipoDocumento(String tipoDocumento) {
    return (root, query, builder) -> 
      builder.equal(root.get("tipoDocumento"), tipoDocumento);
  }
}
