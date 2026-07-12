package cl.duoc.ms_hospitalizacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.repository.HospitalizacionRepository;
import cl.duoc.ms_hospitalizacion.exception.BadRequestException;
import cl.duoc.ms_hospitalizacion.exception.ResourceNotFoundException;

@Service
public class HospitalizacionService {

    @Autowired
    private HospitalizacionRepository hospitalizacionRepository;

   
 public Hospitalizacion guardar(HospitalizacionDTO dto) {
        // Reglas de Integridad y Validación del Negocio
        if (dto.getIdMascota() == null || dto.getIdMascota() <= 0) {
            throw new BadRequestException("El ID de la mascota debe ser un identificador válido.");
        }
        if (dto.getIdDueno() == null || dto.getIdDueno() <= 0) {
            throw new BadRequestException("El ID del dueño debe ser un identificador válido.");
        } 
        
        if (dto.getFecha_alta().isBefore(dto.getFecha_inicio())) {
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (dto.getFecha_inicio() == null || dto.getFecha_alta() == null) {
            throw new BadRequestException("La fecha de alta no puede ser anterior a la fecha de inicio.");
        }
        
       

        Hospitalizacion h = new Hospitalizacion();
        h.setIdMascota(dto.getIdMascota());
        h.setIdDueno(dto.getIdDueno());
        h.setFecha_inicio(dto.getFecha_inicio());
        h.setFecha_alta(dto.getFecha_alta());
        h.setDiagnostico(dto.getDiagnostico());
        return hospitalizacionRepository.save(h);
    }

    public List<Hospitalizacion> listar() {
        return hospitalizacionRepository.findAll();
    }

    
    public Optional<Hospitalizacion> findById(Long id) {
        return hospitalizacionRepository.findById(id);
    }

  public Hospitalizacion actualizar(Long id, Hospitalizacion datosNuevos) {
        if (datosNuevos.getFecha_alta().isBefore(datosNuevos.getFecha_inicio())) {
            throw new BadRequestException("La fecha de alta modificada no puede ser anterior a la fecha de inicio.");
        }

        return hospitalizacionRepository.findById(id)
                .map(h -> {
                    h.setIdMascota(datosNuevos.getIdMascota());
                    h.setIdDueno(datosNuevos.getIdDueno());
                    h.setFecha_inicio(datosNuevos.getFecha_inicio());
                    h.setFecha_alta(datosNuevos.getFecha_alta());
                    h.setDiagnostico(datosNuevos.getDiagnostico());
                    return hospitalizacionRepository.save(h);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Hospitalización no encontrada con ID: " + id));
    }

   public void eliminar(Long id) {
        if (!hospitalizacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, registro inexistente: " + id);
        }
        hospitalizacionRepository.deleteById(id);
    }

    // Verifica si existe (Usado por el endpoint /exists)
    public boolean existePorId(Long id) {
        return hospitalizacionRepository.existsById(id);
    }
}
