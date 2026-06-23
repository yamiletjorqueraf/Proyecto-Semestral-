package cl.duoc.ms_hospitalizacion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.exception.ResourceNotFoundException;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.repository.HospitalizacionRepository;

@Service
public class HospitalizacionService {
 @Autowired
    private HospitalizacionRepository repository;

    public HospitalizacionDTO guardar(HospitalizacionDTO dto) {
        Hospitalizacion entity = modelMapper(dto);
        Hospitalizacion guardado = repository.save(entity);
        return dtoMapper(guardado);
    }

    public List<HospitalizacionDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::dtoMapper)
                .collect(Collectors.toList());
    }

    public HospitalizacionDTO obtenerPorId(Long id) {
        Hospitalizacion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospitalización no encontrada con ID: " + id));
        return dtoMapper(entity);
    }

    private Hospitalizacion modelMapper(HospitalizacionDTO dto) {
        Hospitalizacion h = new Hospitalizacion();
        h.setIdHospitalizacion(dto.getIdHospitalizacion());
        h.setIdMascota(dto.getIdMascota());
        h.setIdDueno(dto.getIdDueno());
        h.setFecha_inicio(dto.getFecha_inicio());
        h.setFecha_alta(dto.getFecha_alta());
        h.setDiagnostico(dto.getDiagnostico());
        return h;
    }

    private HospitalizacionDTO dtoMapper(Hospitalizacion h) {
        HospitalizacionDTO dto = new HospitalizacionDTO();
        dto.setIdHospitalizacion(h.getIdHospitalizacion());
        dto.setIdMascota(h.getIdMascota());
        dto.setIdDueno(h.getIdDueno());
        dto.setFecha_inicio(h.getFecha_inicio());
        dto.setFecha_alta(h.getFecha_alta());
        dto.setDiagnostico(h.getDiagnostico());
        return dto;
    }
}
