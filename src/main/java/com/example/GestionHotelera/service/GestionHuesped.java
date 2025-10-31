package com.example.GestionHotelera.service;

import java.util.List;

import com.example.GestionHotelera.repository.HuespedDAO;
import com.example.GestionHotelera.model.Huesped;

public class GestionHuesped {
  private final HuespedDAO huespedDAO;
  public GestionHuesped(HuespedDAO huespedDAO) {
    this.huespedDAO = huespedDAO;
  }
  public List<Huesped> listarHuespedes() {
    return huespedDAO.findAll();
  }
  public Huesped guardarHuesped(Huesped huesped) {
    return huespedDAO.save(huesped);
  }

}
