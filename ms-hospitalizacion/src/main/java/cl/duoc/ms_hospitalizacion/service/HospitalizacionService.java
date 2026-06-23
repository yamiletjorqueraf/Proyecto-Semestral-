package cl.duoc.ms_hospitalizacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.repository.HospitalizacionRepository;
import cl.duoc.ms_hospitalizacion.exception.ResourceNotFoundException;

@Service
public class HospitalizacionService {

    @Autowired
    private HospitalizacionRepository hospitalizacionRepository;

   
    public Hospitalizacion guardar(HospitalizacionDTO dto) {
        Hospitalizacion h = dto.toModel();
        return hospitalizacionRepository.save(h);
    }

    public List<Hospitalizacion> listar() {
        return hospitalizacionRepository.findAll();
    }

    // Busca opcionalmente por ID
    public Optional<Hospitalizacion> findById(Long id) {
        return hospitalizacionRepository.findById(id);
    }

    // Actualiza los datos controlando si el ID no existe
    public Hospitalizacion actualizar(Long id, Hospitalizacion datosNuevos) {
        return hospitalizacionRepository.findById(id)
                .map(h -> {
                    h.setIdMascota(datosNuevos.getIdMascota());
                    h.setIdDueno(datosNuevos.getIdDueno());
                    h.setFecha_inicio(datosNuevos.getFecha_inicio());
                    h.setFecha_alta(datosNuevos.getFecha_alta());
                    h.setDiagnostico(datosNuevos.getDiagnostico());
                    return hospitalizacionRepository.save(h);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Registro de hospitalización no encontrado con ID: " + id));
    }

    // Elimina verificando existencia previa con el operador '!'
    public void eliminar(Long id) {
        if (!hospitalizacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, ID inexistente: " + id);
        }
        hospitalizacionRepository.deleteById(id);
    }

    // Verifica si existe (Usado por el endpoint /exists)
    public boolean existePorId(Long id) {
        return hospitalizacionRepository.existsById(id);
    }
}
