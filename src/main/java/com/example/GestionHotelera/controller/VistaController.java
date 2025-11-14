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
    model.addAttribute("nuevoHuespedDTO", new HuespedDTO());
    return "index";}

  @PostMapping("/crear-huesped")
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
        System.out.println("El huésped con el número de documento " + nuevoHuespedDTO.getNroDocumento() + " ya existe.");
        return "index";
      }
    }
    gestionHuesped.darDeAltaHuesped(nuevoHuespedDTO);
    redirectAttributes.addFlashAttribute("success", "El huésped " + nuevoHuespedDTO.getNombre() + " " + nuevoHuespedDTO.getApellido() + " ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro?");
    redirectAttributes.addFlashAttribute("title", "Dar de alta Huesped");
    redirectAttributes.addFlashAttribute("nuevoHuespedDTO", new HuespedDTO());
    return "redirect:/";
  }
}


