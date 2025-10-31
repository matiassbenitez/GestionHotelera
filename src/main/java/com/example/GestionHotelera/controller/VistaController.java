package com.example.GestionHotelera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class VistaController {
  @GetMapping("/")//localhost:8080/
  public String mostrarInicio(Model model) {
    model.addAttribute("title", "Esto es un titulo dinámico");
    return "index";}
  
}

