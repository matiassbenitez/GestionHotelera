package com.example.GestionHotelera.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.GestionHotelera.model.Huesped;
import com.example.GestionHotelera.model.Ubicacion;
import com.example.GestionHotelera.DTO.HuespedDTO;
import com.example.GestionHotelera.model.Direccion;
import com.example.GestionHotelera.model.Departamento;
import com.example.GestionHotelera.model.Contacto;
import com.example.GestionHotelera.model.PosicionFiscal;
import com.example.GestionHotelera.repository.HuespedDAO;
import com.example.GestionHotelera.repository.DireccionDAO;
// import com.example.GestionHotelera.model.Contacto;
// import com.example.GestionHotelera.model.Departamento;
// import com.example.GestionHotelera.model.PosicionFiscal;
// import com.example.GestionHotelera.model.Ubicacion;
// import com.example.GestionHotelera.repository.ContactoDAO;
// import com.example.GestionHotelera.repository.UbicacionDAO;
// import com.example.GestionHotelera.repository.DepartamentoDAO;
// import com.example.GestionHotelera.repository.PosicionFiscalDAO;

@Service
public class GestionHuesped {
  
  private final HuespedDAO huespedDAOImpl;
  private final DireccionDAO direccionDAOImpl;
  private final GestionDireccion gestionDireccion;
  //private final ContactoDAO contactoDAO;
  //private final UbicacionDAO ubicacionDAO;
 // private final DepartamentoDAO departamentoDAO;
  //private final PosicionFiscalDAO posicionFiscalDAO;

  public GestionHuesped(HuespedDAO huespedDAO, DireccionDAO direccionDAO, GestionDireccion gestionDireccion) {
    this.huespedDAOImpl = huespedDAO;
    this.direccionDAOImpl = direccionDAO;
    this.gestionDireccion = gestionDireccion;
  }

  public List<Huesped> listarHuespedes() {
    return huespedDAOImpl.findAll();
  }

  public Optional<Huesped> buscarPorNroDocumentoAndTipoDocumento(int nroDocumento, String tipoDocumento) {
    return huespedDAOImpl.findByNroDocumentoAndTipoDocumento(nroDocumento, tipoDocumento);
  }

  public Huesped darDeAltaHuesped(HuespedDTO nuevoHuespedDTO) {
   
    Huesped nuevoHuesped = new Huesped();
    nuevoHuesped.setNombre(nuevoHuespedDTO.getNombre());
    nuevoHuesped.setApellido(nuevoHuespedDTO.getApellido());
    nuevoHuesped.setTipoDocumento(nuevoHuespedDTO.getTipoDocumento());
    nuevoHuesped.setNroDocumento(nuevoHuespedDTO.getNroDocumento());
    nuevoHuesped.setFechaNac(nuevoHuespedDTO.getFechaNac());
    nuevoHuesped.setNacionalidad(nuevoHuespedDTO.getNacionalidad());

    Optional<Direccion> direccionExistente = 
      gestionDireccion.buscarDireccion(nuevoHuespedDTO.getCalle(), nuevoHuespedDTO.getNumero(), nuevoHuespedDTO.getDepartamento(), nuevoHuespedDTO.getPiso());
    if (!direccionExistente.isPresent()) {
      Direccion direccion = new Direccion(); //corroborar que no exista ya esa direccion
      direccion.setCalle(nuevoHuespedDTO.getCalle());
      direccion.setNumero(nuevoHuespedDTO.getNumero());

    if (nuevoHuespedDTO.getPiso() != 0 || nuevoHuespedDTO.getDepartamento() != null) {
      Departamento departamento = new Departamento();
      if (nuevoHuespedDTO.getDepartamento() != null && !nuevoHuespedDTO.getDepartamento().isEmpty()) {
        departamento.setDepartamento(nuevoHuespedDTO.getDepartamento());
      }
      if (nuevoHuespedDTO.getPiso() != 0) {
        departamento.setPiso(nuevoHuespedDTO.getPiso());
      }
      direccion.setDepartamento(departamento);
    }
    direccionDAOImpl.save(direccion);
    nuevoHuesped.setDireccion(direccion);

    } else {
      nuevoHuesped.setDireccion(direccionExistente.get());
    }

    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLocalidad(nuevoHuespedDTO.getLocalidad());
    ubicacion.setProvincia(nuevoHuespedDTO.getProvincia());
    ubicacion.setPais(nuevoHuespedDTO.getPais());
    ubicacion.setCodPostal(nuevoHuespedDTO.getCodPostal());

    Contacto contacto = new Contacto();
    contacto.setTelefono(nuevoHuespedDTO.getPrefijo()+nuevoHuespedDTO.getTelefono());
    if (nuevoHuespedDTO.getEmail() != null && !nuevoHuespedDTO.getEmail().isEmpty()) {
      contacto.setEmail(nuevoHuespedDTO.getEmail());
    }

    PosicionFiscal posicionFiscal = new PosicionFiscal();
    posicionFiscal.setPosicionIVA(nuevoHuespedDTO.getPosicionIva());
    if (nuevoHuespedDTO.getCuit() != null && !nuevoHuespedDTO.getCuit().isEmpty()) {
      posicionFiscal.setCuit(nuevoHuespedDTO.getCuit());
    }
    posicionFiscal.setOcupacion(nuevoHuespedDTO.getOcupacion());

    nuevoHuesped.setPosicionFiscal(posicionFiscal);
    nuevoHuesped.setContacto(contacto);
    nuevoHuesped.setUbicacion(ubicacion);
    
    return huespedDAOImpl.save(nuevoHuesped);
  }


}
