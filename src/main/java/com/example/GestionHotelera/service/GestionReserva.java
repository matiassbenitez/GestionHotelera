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
  private final GestionHabitacion gestionHabitacion;
  private final GestionEstado gestionEstado;
 
  
  public GestionReserva(ReservaDAO reservaDAOImpl, GestionEstado gestionEstado, GestionHabitacion gestionHabitacion) {
    this.reservaDAOImpl = reservaDAOImpl;
    this.gestionHabitacion = gestionHabitacion;
    this.gestionEstado = gestionEstado;
  }

  public List<datosParaReservaDTO> obtenerInfoReserva (Integer nroHabitacion,LocalDate fechaInicio, LocalDate fechaFin){

   Habitacion habitacion = gestionHabitacion.buscarPorNumero(nroHabitacion);
   List<datosParaReservaDTO> infoReserva = obtenerInfoReserva(habitacion, fechaFin, fechaInicio);



    return infoReserva;

  }



















  public void reservar(datosParaReservaDTO datos) {
    Reserva reserva = new Reserva();
    reserva.setFechaReserva(LocalDate.now());
    reserva.setIngreso(datos.getFechaInicio());
    reserva.setEgreso(datos.getFechaFin());

    Habitacion habitacion = gestionHabitacion.buscarPorNumero(datos.getNumeroHabitacion());
    reserva.setHabitacion(habitacion);

    reserva.setNombre(datos.getNombre());
    reserva.setApellido(datos.getApellido());
    reserva.setTelefono(datos.getTelefono());
    reservaDAOImpl.save(reserva); 

    //gestionEstado.crearEstado(habitacion, datos.getFechaInicio(), datos.getFechaFin());

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
