package com.example.GestionHotelera.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionHotelera.model.Habitacion;
import com.example.GestionHotelera.model.TipoHabitacion;
import java.util.List;

public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {
    public List<Habitacion> findByTipo(TipoHabitacion tipo);
    public Habitacion findByNumero(int numero);
}
