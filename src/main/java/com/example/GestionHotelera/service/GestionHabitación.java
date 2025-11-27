package com.example.GestionHotelera.service;
import org.springframework.stereotype.Service;
import com.example.GestionHotelera.repository.HabitacionDAO;
import com.example.GestionHotelera.service.GestionEstado;

@Service
public class GestionHabitación {
  private final HabitacionDAO habitacionDAOImpl;
  private final GestionEstado gestionEstado;
  public GestionHabitación(HabitacionDAO habitacionDAOImpl, GestionEstado gestionEstado) {
    this.habitacionDAOImpl = habitacionDAOImpl;
    this.gestionEstado = gestionEstado;
  }
}
