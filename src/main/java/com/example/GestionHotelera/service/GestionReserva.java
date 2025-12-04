package com.example.GestionHotelera.service;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import com.example.GestionHotelera.DTO.datosParaReservaDTO;
import com.example.GestionHotelera.model.Reserva;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.repository.ReservaDAO;

@Service
public class GestionReserva {
  ReservaDAO reservaDAOImpl;
  public GestionReserva(ReservaDAO reservaDAOImpl) {
    this.reservaDAOImpl = reservaDAOImpl;
  }
  public void reservar(datosParaReservaDTO datos, Habitacion habitacion) {
    Reserva reserva = new Reserva();
    reserva.setFechaReserva(LocalDate.now());
    reserva.setIngreso(datos.getFechaInicio());
    reserva.setEgreso(datos.getFechaFin());
    reserva.setHabitacion(habitacion);
    reserva.setNombre(datos.getNombre());
    reserva.setApellido(datos.getApellido());
    reserva.setTelefono(datos.getTelefono());
    reservaDAOImpl.save(reserva); 
  }
  public List<datosParaReservaDTO> obtenerInfoReserva(Habitacion habitacion, LocalDate fechaFin, LocalDate fechaInicio) {
    List<Reserva> infoReservas = reservaDAOImpl.findByHabitacionAndIngresoLessThanEqualAndEgresoGreaterThanEqual(habitacion, fechaFin, fechaInicio);
    System.out.println("Habitacion Nro: " + habitacion.getNumero());
    System.out.println("fecha Fin: " + fechaFin);
    System.out.println("fecha Inicio: " + fechaInicio);
    return infoReservas.stream().map(reserva -> new datosParaReservaDTO(
        habitacion.getNumero(),
        reserva.getIngreso(),
        reserva.getEgreso(),
        reserva.getNombre(),
        reserva.getApellido(),
        reserva.getTelefono()
    )).toList();
  }
}
