package com.example.GestionHotelera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.service.GestionHuesped;
import com.example.GestionHotelera.model.TipoHabitacion;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Controller
public class VistaController {
  private final GestionHuesped gestionHuesped;

  public VistaController(GestionHuesped gestionHuesped) {
    this.gestionHuesped = gestionHuesped;
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
  public String mostrarReservarHabitacion(Model model) {
    model.addAttribute("title", "Reservar Habitaciones");
    model.addAttribute("viewName", "reservarHabitacion");
    model.addAttribute("tipos", TipoHabitacion.values());
    return "layout";}

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