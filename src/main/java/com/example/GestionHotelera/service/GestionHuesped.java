package com.example.GestionHotelera.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
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
import com.example.GestionHotelera.specifications.HuespedSpecification;


@Service
public class GestionHuesped {
  
  private final HuespedDAO huespedDAOImpl;
  private final DireccionDAO direccionDAOImpl;
  private final GestionDireccion gestionDireccion;

  public GestionHuesped(HuespedDAO huespedDAO, DireccionDAO direccionDAO, GestionDireccion gestionDireccion) {
    this.huespedDAOImpl = huespedDAO;
    this.direccionDAOImpl = direccionDAO;
    this.gestionDireccion = gestionDireccion;
  }

  public List<Huesped> listarHuespedes() {
    return huespedDAOImpl.findAll();
  }

  public Optional<HuespedDTO> buscarPorNroDocumentoAndTipoDocumento(String nroDocumento, String tipoDocumento) {
    return huespedDAOImpl
            .findByNroDocumentoAndTipoDocumento(nroDocumento, tipoDocumento)
            .map(this::mapperToDTO);
}

  public HuespedDTO darDeAltaHuesped(HuespedDTO nuevoHuespedDTO) {
   
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
      Direccion direccion = new Direccion();
      direccion.setCalle(nuevoHuespedDTO.getCalle());
      direccion.setNumero(nuevoHuespedDTO.getNumero());

    if (!nuevoHuespedDTO.getPiso().isEmpty() || nuevoHuespedDTO.getDepartamento() != null) {
      Departamento departamento = new Departamento();
      if (nuevoHuespedDTO.getDepartamento() != null && !nuevoHuespedDTO.getDepartamento().isEmpty()) {
        departamento.setDepartamento(nuevoHuespedDTO.getDepartamento());
      }
      if (!nuevoHuespedDTO.getPiso().isEmpty()) {
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
    
    nuevoHuesped = huespedDAOImpl.save(nuevoHuesped);
    HuespedDTO huespedRetorno = mapperToDTO(nuevoHuesped);

    return huespedRetorno;
  }

  public List<Huesped> buscarHuespedesPorCriterios(String apellido, String nombre, String tipoDocumento, String nroDocumento) {
    
    Specification<Huesped> spec = Specification.not(null);
    if (apellido != null && !apellido.isEmpty()) {
      spec = spec.and(HuespedSpecification.apellidoStartsWith(apellido.trim()));
    }
    if (nombre != null && !nombre.isEmpty()) {
      spec = spec.and(HuespedSpecification.nombreStartsWith(nombre.trim()));
    }
    if (tipoDocumento != null && !tipoDocumento.trim().isEmpty()) {
      spec = spec.and(HuespedSpecification.hasTipoDocumento(tipoDocumento.trim()));
    }
    if (nroDocumento != null && !nroDocumento.trim().isEmpty()) {
      spec = spec.and(HuespedSpecification.hasNroDocumento(nroDocumento.trim()));
    }
    return huespedDAOImpl.findAll(spec);
  }

  public Huesped buscarPorId(Long id) {
    return huespedDAOImpl.findById(id).orElse(null);
  }

  public Huesped guardarHuesped(Huesped huesped) {
    
    return huespedDAOImpl.save(huesped);
  }


    public HuespedDTO mapperToDTO(Huesped huesped) {
        if (huesped == null) {
            return null;
        }

        HuespedDTO dto = new HuespedDTO();

        
        dto.setNombre(huesped.getNombre());
        dto.setApellido(huesped.getApellido());
        dto.setTipoDocumento(huesped.getTipoDocumento());
        dto.setNroDocumento(huesped.getNroDocumento());
        dto.setFechaNac(huesped.getFechaNac());
        dto.setNacionalidad(huesped.getNacionalidad());

        
        Direccion direccion = huesped.getDireccion();
        if (direccion != null) {
            dto.setCalle(direccion.getCalle());
            dto.setNumero(direccion.getNumero());

            
            Departamento departamento = direccion.getDepartamento();
            if (departamento != null) {
                
                dto.setDepartamento(departamento.getDepartamento());
                dto.setPiso(departamento.getPiso());
            } else {
                 dto.setDepartamento(null);
                 dto.setPiso(null);
            }
        }

        
        Ubicacion ubicacion = huesped.getUbicacion();
        if (ubicacion != null) {
            dto.setLocalidad(ubicacion.getLocalidad());
            dto.setProvincia(ubicacion.getProvincia());
            dto.setPais(ubicacion.getPais());
            dto.setCodPostal(ubicacion.getCodPostal());
            
        }

        
        Contacto contacto = huesped.getContacto();
        if (contacto != null) {
            dto.setTelefono(contacto.getTelefono());
            dto.setEmail(contacto.getEmail());
        }

        
        PosicionFiscal posicionFiscal = huesped.getPosicionFiscal();
        if (posicionFiscal != null) {
            dto.setOcupacion(posicionFiscal.getOcupacion());
            dto.setPosicionIva(posicionFiscal.getPosicionIVA());
            dto.setCuit(posicionFiscal.getCuit());
        }

        return dto;
    }


}
