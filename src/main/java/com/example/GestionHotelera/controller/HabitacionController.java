package com.example.GestionHotelera.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import java.time.LocalDate;
import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.DTO.datosParaReservaDTO;
import com.example.GestionHotelera.service.GestionEstado;
import com.example.GestionHotelera.service.GestionHabitacion;
import com.example.GestionHotelera.service.GestionReserva;
import com.example.GestionHotelera.model.TipoHabitacion;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@Controller
public class HabitacionController {

  private final GestionEstado gestionEstado;
  private final GestionHabitacion gestionHabitacion;
  private final GestionReserva gestionReserva;

  public HabitacionController(GestionEstado gestionEstado, GestionHabitacion gestionHabitacion, GestionReserva gestionReserva) {
    this.gestionEstado = gestionEstado;
    this.gestionHabitacion = gestionHabitacion;
    this.gestionReserva = gestionReserva;
  }

  
  @GetMapping("/habitaciones/estado")
  public String mostrarEstadoHabitaciones(Model model) {
    model.addAttribute("title", "Estado de las Habitaciones");
    model.addAttribute("viewName", "estadoHabitaciones");
    return "layout";}

 
  @GetMapping("/habitaciones/reservar")
  public String mostrarReservarHabitacion(Model model,
    @RequestParam(required = false) LocalDate fechaInicio,
    @RequestParam(required = false) LocalDate fechaFin,
    @RequestParam(required = false) String tipo)
    {
    model.addAttribute("title", "Reservar Habitaciones");
    model.addAttribute("viewName", "reservarHabitacion");
    model.addAttribute("tipos", TipoHabitacion.values());
    boolean buscar = tipo != null && fechaInicio != null && fechaFin != null;
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    if (buscar) {
      System.out.println(fechaInicio);
      tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, TipoHabitacion.valueOf(tipo));
      System.out.println(fechaFin);
      System.out.println(tablaEstados.get(0).getEstadosPorHabitacion());
      model.addAttribute("mostrarTabla", buscar);
    }
    model.addAttribute("tablaEstados", tablaEstados);
    model.addAttribute("tipoSeleccionado", tipo);
    model.addAttribute("fechaInicioSeleccionada", fechaInicio);
    model.addAttribute("fechaFinSeleccionada", fechaFin);
    return "layout";}
    
  
  @PostMapping("/habitaciones/reservar")
  @ResponseBody
  public ResponseEntity<?> procesarReservaHabitacion(Model model,
    @RequestBody List<datosParaReservaDTO> datosReserva) {
      try{
        for (datosParaReservaDTO datos : datosReserva) {
          gestionReserva.reservar(datos);
          gestionEstado.crearEstadoReservada(datos.getNumeroHabitacion(), datos.getFechaInicio(), datos.getFechaFin());
        }
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "Reservas procesadas con éxito."));
      } catch (Exception e) {
        System.err.println("Error al procesar la reserva: " + e.getMessage());
        return ResponseEntity.badRequest()
                             .body(java.util.Collections.singletonMap("error", "Error al crear la reserva: " + e.getMessage()));
      }
    }
  
 
  @GetMapping("/habitaciones/infoReserva")
  @ResponseBody
  public List<datosParaReservaDTO> obtenerInfoReserva(
    @RequestParam String numeroHabitacion,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {

    List<datosParaReservaDTO> infoReserva = gestionReserva.obtenerInfoReserva(Integer.parseInt(numeroHabitacion), fechaInicio, fechaFin);
    return infoReserva;
  }

  
  @GetMapping("/habitaciones/ocupar")
  public String mostrarOcuparHabitacion(Model model,
    @RequestParam(required = false) LocalDate fechaInicio,
    @RequestParam(required = false) LocalDate fechaFin,
    @RequestParam(required = false) String tipo)
    {
    model.addAttribute("title", "Ocupar Habitaciones");
    model.addAttribute("viewName", "ocuparHabitacion");
    model.addAttribute("tipos", TipoHabitacion.values());
    boolean buscar = tipo != null && fechaInicio != null && fechaFin != null;
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    if (buscar) {
      System.out.println(fechaInicio);
      tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, TipoHabitacion.valueOf(tipo));
      System.out.println(fechaFin);
      System.out.println(tablaEstados.get(0).getEstadosPorHabitacion());
      model.addAttribute("mostrarTabla", buscar);
    }
    model.addAttribute("tablaEstados", tablaEstados);
    model.addAttribute("tipoSeleccionado", tipo);
    model.addAttribute("fechaInicioSeleccionada", fechaInicio);
    model.addAttribute("fechaFinSeleccionada", fechaFin);
    return "layout";}

  
  @PostMapping("/habitacion/cambiarEstado")
  public String guardarEstadoHabitacion(Model model,
    @RequestParam Integer numeroHabitacion,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
    @RequestParam(required = false) boolean cargarOtro, 
    @RequestParam(required = false) boolean salir)
    {
    if (cargarOtro || salir){

      gestionHabitacion.guardarEstadoHabitacion(numeroHabitacion, fechaInicio, fechaFin);

      if (salir){
        System.out.println("Salir y volver al home");
        model.addAttribute("title", "Gestión Hotelera - Home");
        model.addAttribute("viewName", "index");
        return "redirect:/";
      } else {
        model.addAttribute("title", "Ocupar Habitacion");
        model.addAttribute("viewName", "ocuparHabitacion");
        return "redirect:/habitaciones/ocupar";
      }
    }
    model.addAttribute("title", "Gestión Hotelera - Home");
    model.addAttribute("viewName", "index");
    return "layout";
  }
}