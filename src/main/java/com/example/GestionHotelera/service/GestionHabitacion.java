package com.example.GestionHotelera.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.TipoHabitacion;
import com.example.GestionHotelera.repository.EstadiaDAO;
import com.example.GestionHotelera.repository.EstadoDAO;
import com.example.GestionHotelera.repository.HabitacionDAO;
import com.example.GestionHotelera.repository.ReservaDAO;

@Service

public class GestionHabitacion {
    private HabitacionDAO habitacionDAO;
    private EstadoDAO estadoDAO;
    
    public GestionHabitacion(
    HabitacionDAO habitacionDAO,
        EstadiaDAO estadiaDAO,
    ReservaDAO reservaDAO) {
     this.habitacionDAO = habitacionDAO;
     this.estadoDAO = estadoDAO;
    
    }
    public List<Habitacion> obtenerEstadoHabitaciones(String desde, String hasta) {

    LocalDate fechaDesde = LocalDate.parse(desde);
    LocalDate fechaHasta = LocalDate.parse(hasta);

    return habitacionDAO.obtenerEstadoHabitaciones(fechaDesde, fechaHasta);
}
   

public Map<LocalDate, Map<Habitacion, String>> obtenerMapaDisponibilidad(
        LocalDate desde,
        LocalDate hasta,
        String tipoHabitacionSeleccionado) {

    List<Habitacion> habitaciones;

    if (tipoHabitacionSeleccionado != null && !tipoHabitacionSeleccionado.isEmpty()) {
       
        habitaciones = habitacionDAO.findByTipo(
                TipoHabitacion.valueOf(tipoHabitacionSeleccionado)
        );
    } else {
        habitaciones = habitacionDAO.findAll();
    }

    Map<LocalDate, Map<Habitacion, String>> grilla = new LinkedHashMap<>();

    for (LocalDate fecha = desde; !fecha.isAfter(hasta); fecha = fecha.plusDays(1)) {
        Map<Habitacion, String> estadoPorHabitacion = new LinkedHashMap<>();

        for (Habitacion hab : habitaciones) {
           
            String estado = estadoDAO.EstadoEnFecha(hab.getId(), fecha);
           

           if (estado.equals("OCUPADA")) {
               estadoPorHabitacion.put(hab, "OCUPADA");
           } else if (estado.equals("RESERVADA")) {
               estadoPorHabitacion.put(hab, "RESERVADA");
           } if(estado.equals("FUERA_DE_SERVICIO")) {
               estadoPorHabitacion.put(hab, "FUERA_DE_SERVICIO");
           } else {
               estadoPorHabitacion.put(hab, "LIBRE");
           }
        }
        grilla.put(fecha, estadoPorHabitacion);
    }

    return grilla;
}
}
