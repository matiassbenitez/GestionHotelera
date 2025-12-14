package com.example.GestionHotelera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.time.LocalDate;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.service.GestionHuesped;
import com.example.GestionHotelera.service.GestionHabitacion;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.springframework.format.annotation.DateTimeFormat;


@Controller
public class HuespedController {

  private final GestionHuesped gestionHuesped;
  private final GestionHabitacion gestionHabitacion; // Se necesita para el POST /huesped/buscarOcupantes

  public HuespedController(GestionHuesped gestionHuesped, GestionHabitacion gestionHabitacion) {
    this.gestionHuesped = gestionHuesped;
    this.gestionHabitacion = gestionHabitacion;
  }

  
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
    Boolean documentoRepetido = gestionHuesped.existeHuespedConDocumento(nuevoHuespedDTO.getNroDocumento(), nuevoHuespedDTO.getTipoDocumento());
    
    if (!forzar){
      if (documentoRepetido == true) {
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
    redirectAttributes.addFlashAttribute("nuevoHuespedDTO", new HuespedDTO());
    return "redirect:/huesped/crear";
  }

 
  @GetMapping("/huesped/buscar")
  public String mostrarBuscarHuesped(
    @RequestParam(required = false) String apellido,
    @RequestParam(required = false) String nombre,
    @RequestParam(required = false) String tipo,
    @RequestParam(required = false) String dni,
    @RequestParam(required = false) String buscar,
    Model model) {
    List<HuespedDTO> resultados = new ArrayList<>();
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

  
  @GetMapping("/huesped/buscarOcupantes")
  public String mostrarBuscarOcupantes(
    @RequestParam(required = false) Boolean preguntar,
    @RequestParam(required = false) Integer numeroHabitacion,
    @RequestParam(required = false) LocalDate fechaInicio,
    @RequestParam(required = false) LocalDate fechaFin,
    @RequestParam(required = false) String apellido,
    @RequestParam(required = false) String nombre,
    @RequestParam(required = false) String tipo,
    @RequestParam(required = false) String dni,
    @RequestParam(required = false) String buscar,
    Model model) {
    List<HuespedDTO> resultados = new ArrayList<>();
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
    model.addAttribute("title", "Buscar Ocupante");
    model.addAttribute("viewName", "buscarOcupantes");
    return "layout";}

  
  @PostMapping("huesped/buscarOcupantes")
  @ResponseBody
  public Map<String, String> procesarOcuparHabitacion(Model model,
    @RequestParam Long idHuesped,
    @RequestParam Integer numeroHabitacion,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
      
      gestionHabitacion.procesarOcuparHabitacion(idHuesped,numeroHabitacion,fechaInicio,fechaFin);
      
      String urlDestino = "/huesped/buscarOcupantes?preguntar=true&numeroHabitacion=" + numeroHabitacion + "&fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;
      Map<String, String> response = new HashMap<>();
      response.put("status", "success");
      response.put("redirectUrl", urlDestino);
      return response;
  }
}