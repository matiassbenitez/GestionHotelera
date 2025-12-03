package com.example.GestionHotelera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.time.LocalDate;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.DTO.datosParaReservaDTO;
import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.service.GestionHuesped;
import com.example.GestionHotelera.service.GestionEstado;
import com.example.GestionHotelera.service.GestionHabitacion;
import com.example.GestionHotelera.service.GestionReserva;
import com.example.GestionHotelera.model.TipoHabitacion;
import com.example.GestionHotelera.model.Habitacion;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Controller
public class VistaController {

  private final GestionHuesped gestionHuesped;
  private final GestionEstado gestionEstado;
  private final GestionHabitacion gestionHabitacion;
  private final GestionReserva gestionReserva;

  public VistaController(GestionHuesped gestionHuesped,GestionEstado gestionEstado, GestionHabitacion gestionHabitacion, GestionReserva gestionReserva) {
    this.gestionHuesped = gestionHuesped;
    this.gestionEstado = gestionEstado;
    this.gestionHabitacion = gestionHabitacion;
    this.gestionReserva = gestionReserva;
  }

  @GetMapping("/")//localhost:8080/
  public String mostrarHome(Model model) {
    model.addAttribute("title", "Gestión Hotelera - Home");
    model.addAttribute("viewName", "index");
    return "layout";}


  @GetMapping("/huesped/crear")
  public String mostrarCrearHuesped(Model model) {
    model.addAttribute("title", "Dar de alta Huesped");
    model.addAttribute("nuevoHuespedDTO", new HuespedDTO());
    model.addAttribute("viewName", "altaHuesped");
    return "layout";}

  @PostMapping("/huesped/crear")
  public String crearHuesped(
    @ModelAttribute HuespedDTO nuevoHuespedDTO,
    Model model,
    @RequestParam(defaultValue = "false") boolean forzar, 
    RedirectAttributes redirectAttributes) {
    Optional<Huesped> huespedEncontrado = gestionHuesped.buscarPorNroDocumentoAndTipoDocumento(nuevoHuespedDTO.getNroDocumento(), nuevoHuespedDTO.getTipoDocumento());
    if (!forzar){
      if (huespedEncontrado.isPresent()) {
        model.addAttribute("error", "¡CUIDADO! El tipo y número de documento ya existen en el sistema.");
        model.addAttribute("title", "Dar de alta Huesped");
        model.addAttribute("nuevoHuespedDTO", nuevoHuespedDTO);
        model.addAttribute("viewName", "altaHuesped");
        System.out.println("El huésped con el número de documento " + nuevoHuespedDTO.getNroDocumento() + " ya existe.");
        return "layout";
      }
    }
    gestionHuesped.darDeAltaHuesped(nuevoHuespedDTO);
    redirectAttributes.addFlashAttribute("success", "El huésped " + nuevoHuespedDTO.getNombre() + " " + nuevoHuespedDTO.getApellido() + " ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro?");
    redirectAttributes.addFlashAttribute("title", "Dar de alta Huesped");
    //VER SI AGREGAR EL VIEW NAME
    redirectAttributes.addFlashAttribute("nuevoHuespedDTO", new HuespedDTO());
    return "redirect:/huesped/crear";
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
      List<Habitacion> habitaciones = gestionHabitacion.obtenerHabitacionesPorTipo(TipoHabitacion.valueOf(tipo));
      tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, habitaciones);
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
    public String procesarReservaHabitacion(Model model,
      @RequestBody List<datosParaReservaDTO> datosReserva) {
        for (datosParaReservaDTO datos : datosReserva) {
          gestionReserva.reservar(datos);
          Habitacion habitacion = gestionHabitacion.buscarPorNumero(datos.getNumeroHabitacion());
          gestionEstado.crearEstado(habitacion, datos.getFechaInicio(), datos.getFechaFin());
        }
        model.addAttribute("title", "Gestión Hotelera - Home");
        model.addAttribute("viewName", "index");
        return "layout";
      }

  @GetMapping("/huesped/buscar")
  public String mostrarBuscarHuesped(
    @RequestParam(required = false) String apellido,
    @RequestParam(required = false) String nombre,
    @RequestParam(required = false) String tipo,
    @RequestParam(required = false) String dni,
    @RequestParam(required = false) String buscar,
    Model model) {
    List<Huesped> resultados = new ArrayList<>();
    boolean busquedaRealizada = (buscar != null && buscar.equals("true"));
    if (busquedaRealizada) {
      resultados = gestionHuesped.buscarHuespedesPorCriterios(apellido, nombre, tipo, dni);
      System.out.println("Resultados encontrados: " + resultados);
    }
    if (resultados.isEmpty() && busquedaRealizada) {
      model.addAttribute("title", "Dar de alta Huesped");
      model.addAttribute("viewName", "altaHuesped");
      return "redirect:/huesped/crear";
    }
    model.addAttribute("resultados", resultados);
    model.addAttribute("mostrarResultados", busquedaRealizada);
    model.addAttribute("apellido", apellido);
    model.addAttribute("nombre", nombre);
    model.addAttribute("tipo", tipo);
    model.addAttribute("nroDoc", dni);
    model.addAttribute("title", "Buscar Huesped");
    model.addAttribute("viewName", "buscarHuesped");
    return "layout";}


  @GetMapping("/habitacion/ocupar")
  public String mostrarOcuparHabitacion(Model model) {
    model.addAttribute("title", "Ocupar Habitacion");
    model.addAttribute("viewName", "ocuparHabitacion");
    return "layout";}
  
}