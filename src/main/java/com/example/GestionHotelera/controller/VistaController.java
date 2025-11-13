package com.example.GestionHotelera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.model.Direccion;
import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.repository.HuespedDAO;
import com.example.GestionHotelera.service.GestionHuesped;
import java.util.Optional;


@Controller
public class VistaController {
  private final GestionHuesped gestionHuesped;

  public VistaController(GestionHuesped gestionHuesped) {
    this.gestionHuesped = gestionHuesped;
  }

  @GetMapping("/")//localhost:8080/
  public String mostrarInicio(Model model) {
    model.addAttribute("title", "Dar de alta Huesped");
    return "index";}
// ARREGLAR POST DE FORMULARIO, VER CONSTRUCTOR DE GestionHuesped sin argumentos, AGREGAR ATRIBUTOS PARA RELLENAR EL FORMULARIO PARA LA VISTA
  @PostMapping("/crear-huesped")
  public String crearHuesped(@ModelAttribute HuespedDTO nuevoHuespedDTO,Model model) {
    Optional<Huesped> huespedEncontrado = gestionHuesped.buscarPorNroDocumento(nuevoHuespedDTO.getNroDocumento());
    if (huespedEncontrado.isPresent()) {
      model.addAttribute("error", "¡CUIDADO! El tipo y número de documento ya existen en el sistema.");
      model.addAttribute("title", "Dar de alta Huesped");
      model.addAttribute("huespedDTO", nuevoHuespedDTO);
      System.out.println("El huésped con el número de documento " + nuevoHuespedDTO.getNroDocumento() + " ya existe.");
      return "index";
    }
    //return "redirect:/";
    gestionHuesped.darDeAltaHuesped(nuevoHuespedDTO);
    model.addAttribute("success", "El huésped " + nuevoHuespedDTO.getNombre() + " " + nuevoHuespedDTO.getApellido() + " ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro?");
    return "index";
  }
}

