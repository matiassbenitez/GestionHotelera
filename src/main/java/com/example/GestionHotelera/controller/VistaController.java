package com.example.GestionHotelera.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.time.LocalDate;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.DTO.TablaEstadoDTO;
import com.example.GestionHotelera.DTO.datosParaReservaDTO;
import com.example.GestionHotelera.service.GestionHuesped;
import com.example.GestionHotelera.service.GestionEstado;
import com.example.GestionHotelera.service.GestionHabitacion;
import com.example.GestionHotelera.service.GestionReserva;
import com.example.GestionHotelera.model.TipoHabitacion;

import java.util.List;
//import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@Controller
public class VistaController {

  private final GestionHuesped gestionHuesped;
  private final GestionEstado gestionEstado;
  private final GestionHabitacion gestionHabitacion;
  private final GestionReserva gestionReserva;

  public VistaController(GestionHuesped gestionHuesped,GestionEstado gestionEstado, GestionHabitacion gestionHabitacion, GestionReserva gestionReserva) {
    this.gestionHuesped = gestionHuesped;
    this.gestionEstado = gestionEstado;
    this.gestionHabitacion = gestionHabitacion;
    this.gestionReserva = gestionReserva;
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
    //Optional<HuespedDTO> huespedEncontrado = gestionHuesped.buscarPorNroDocumentoAndTipoDocumento(nuevoHuespedDTO.getNroDocumento(), nuevoHuespedDTO.getTipoDocumento());
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
  public String mostrarReservarHabitacion(Model model,
    @RequestParam(required = false) LocalDate fechaInicio,
    @RequestParam(required = false) LocalDate fechaFin,
    @RequestParam(required = false) String tipo)
    {
    model.addAttribute("title", "Reservar Habitaciones");
    model.addAttribute("viewName", "reservarHabitacion");
    model.addAttribute("tipos", TipoHabitacion.values());
    boolean buscar = tipo != null && fechaInicio != null && fechaFin != null;
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    if (buscar) {
      System.out.println(fechaInicio);
      //List<Habitacion> habitaciones = gestionHabitacion.obtenerHabitacionesPorTipo(TipoHabitacion.valueOf(tipo));
      //tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, habitaciones);
      //Las 2 lineas de arriba son codigo viejo

      tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, TipoHabitacion.valueOf(tipo));
     
      System.out.println(fechaFin);
      System.out.println(tablaEstados.get(0).getEstadosPorHabitacion());
      model.addAttribute("mostrarTabla", buscar);
    }
    model.addAttribute("tablaEstados", tablaEstados);
    model.addAttribute("tipoSeleccionado", tipo);
    model.addAttribute("fechaInicioSeleccionada", fechaInicio);
    model.addAttribute("fechaFinSeleccionada", fechaFin);
    return "layout";}
    




    @PostMapping("/habitaciones/reservar")
    @ResponseBody
    public ResponseEntity<?> procesarReservaHabitacion(Model model,
      @RequestBody List<datosParaReservaDTO> datosReserva) {
        try{
          for (datosParaReservaDTO datos : datosReserva) {

            //gestionReserva

            gestionReserva.reservar(datos);
            
            gestionEstado.crearEstadoReservada(datos.getNumeroHabitacion(), datos.getFechaInicio(), datos.getFechaFin());

            //gestionEstado.crearEstado(habitacion, datos.getFechaInicio(), datos.getFechaFin());


          }
          return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "Reservas procesadas con éxito."));
          // model.addAttribute("title", "Gestión Hotelera - Home");
          // model.addAttribute("viewName", "index");
          // return "layout";
      } catch (Exception e) {
        System.err.println("Error al procesar la reserva: " + e.getMessage());
            return ResponseEntity.badRequest() // Código 400
                                 .body(java.util.Collections.singletonMap("error", "Error al crear la reserva: " + e.getMessage()));
        }
      }
    

      
      
      @GetMapping("/habitaciones/infoReserva")
      @ResponseBody
      public List<datosParaReservaDTO> obtenerInfoReserva(
        @RequestParam String numeroHabitacion,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {

          List<datosParaReservaDTO> infoReserva = gestionReserva.obtenerInfoReserva(Integer.parseInt(numeroHabitacion), fechaInicio, fechaFin);

          //Habitacion habitacion = gestionHabitacion.buscarPorNumero(Integer.parseInt(numeroHabitacion));
          //List<datosParaReservaDTO> infoReserva = gestionReserva.obtenerInfoReserva(habitacion, fechaFin, fechaInicio);
          //Estas 2 lineas son de codigo viejo


          return infoReserva;
      }







      @GetMapping("/habitaciones/ocupar")
  public String mostrarOcuparHabitacion(Model model,
    @RequestParam(required = false) LocalDate fechaInicio,
    @RequestParam(required = false) LocalDate fechaFin,
    @RequestParam(required = false) String tipo)
    {
    model.addAttribute("title", "Ocupar Habitaciones");
    model.addAttribute("viewName", "ocuparHabitacion");
    model.addAttribute("tipos", TipoHabitacion.values());
    boolean buscar = tipo != null && fechaInicio != null && fechaFin != null;
    List<TablaEstadoDTO> tablaEstados = new ArrayList<>();
    if (buscar) {
      System.out.println(fechaInicio);
      
      //List<Habitacion> habitaciones = gestionHabitacion.obtenerHabitacionesPorTipo(TipoHabitacion.valueOf(tipo));
      //tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, habitaciones);
     
      tablaEstados = gestionEstado.generarTablaEstados(fechaInicio, fechaFin, TipoHabitacion.valueOf(tipo));
     
      System.out.println(fechaFin);
      System.out.println(tablaEstados.get(0).getEstadosPorHabitacion());
      model.addAttribute("mostrarTabla", buscar);
    }
    model.addAttribute("tablaEstados", tablaEstados);
    model.addAttribute("tipoSeleccionado", tipo);
    model.addAttribute("fechaInicioSeleccionada", fechaInicio);
    model.addAttribute("fechaFinSeleccionada", fechaFin);
    return "layout";}

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


        //Huesped huesped = gestionHuesped.buscarPorId(idHuesped);
        //System.out.println("huesped seleccionado: " + huesped.getNombre() + " " + huesped.getApellido());
        //Habitacion habitacion = gestionHabitacion.buscarPorNumero(numeroHabitacion);
        //System.out.println("habitacion a ocupar: " + habitacion.getNumero());
        //huesped.setHabitacion(habitacion);
        //gestionHuesped.guardarHuesped(huesped);
        //System.out.println("habitacion asignada al huesped: " + huesped.getHabitacion().getNumero());

        //Las lineas que estan arriba de esta son de codigo viejo.
        
        String urlDestino = "/huesped/buscarOcupantes?preguntar=true&numeroHabitacion=" + numeroHabitacion + "&fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("redirectUrl", urlDestino);
        return response;
      }






    @PostMapping("/habitacion/cambiarEstado")
    public String guardarEstadoHabitacion(Model model,
      @RequestParam Integer numeroHabitacion,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      //@RequestParam(required = false) boolean seguir,
      @RequestParam(required = false) boolean cargarOtro, 
      @RequestParam(required = false) boolean salir)
      {
      if (cargarOtro || salir){

        gestionHabitacion.guardarEstadoHabitacion(numeroHabitacion, fechaInicio, fechaFin);

        //Habitacion habitacion = gestionHabitacion.buscarPorNumero(numeroHabitacion);
        //gestionEstado.crearEstadoOcupado(habitacion, fechaInicio, fechaFin);


        if (salir){
          model.addAttribute("title", "Gestión Hotelera - Home");
          model.addAttribute("viewName", "index");
          return "layout";
        } else {
          model.addAttribute("title", "Ocupar Habitacion");
          model.addAttribute("viewName", "ocuparHabitacion");
          return "layout";
        }
      }
      
      // if (seguir){
      //    return "redirect:/huesped/buscarOcupantes?preguntar=false&numeroHabitacion=" + numeroHabitacion + "&fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;
      
      // }
      model.addAttribute("title", "Gestión Hotelera - Home");
      model.addAttribute("viewName", "index");
      return "layout";
    }

  // @GetMapping("/habitacion/ocupar")
  // public String mostrarOcuparHabitacion(Model model) {
  //   model.addAttribute("tipos", TipoHabitacion.values());
  //   model.addAttribute("title", "Ocupar Habitacion");
  //   model.addAttribute("viewName", "ocuparHabitacion");
  //   return "layout";}

    
  
}