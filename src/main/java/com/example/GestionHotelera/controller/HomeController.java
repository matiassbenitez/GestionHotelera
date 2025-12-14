package com.example.GestionHotelera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {


  @GetMapping("/")
  public String mostrarHome(Model model) {
    model.addAttribute("title", "Gestión Hotelera - Home");
    model.addAttribute("viewName", "index");
    return "layout";}
}