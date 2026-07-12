package cl.duoc.ms_personal.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.duoc.ms_personal.exception.ResourceNotFoundException;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.repository.PersonalRepository;

@Service
public class PersonalService {

    private static final Logger logger = LoggerFactory.getLogger(PersonalService.class);

    private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public List<Personal> listar() {
        logger.info("Listando todo el personal");
        List<Personal> lista = personalRepository.findAll();
        logger.info("Total personal encontrado: {}", lista.size());
        return lista;
    }

    public Personal guardar(Personal personal) {
        logger.info("Guardando personal: nombre={}, cargo={}", personal.getNombre(), personal.getCargo());
        Personal guardado = personalRepository.save(personal);
        logger.info("Personal guardado exitosamente con id={}", guardado.getIdPersonal());
        return guardado;
    }

    public Personal actualizar(Long id, Personal datosNuevos) {
        logger.info("Actualizando personal id={}", id);
        
        // CORREGIDO: Lanzar ResourceNotFoundException específica en lugar de RuntimeException genérico
        Personal existente = personalRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Miembro del personal no encontrado para actualizar id={}", id);
                return new ResourceNotFoundException("Miembro del personal no encontrado con el ID: " + id);
            });
        
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setCargo(datosNuevos.getCargo());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setActivo(datosNuevos.isActivo());
        
        Personal actualizado = personalRepository.save(existente);
        logger.info("Personal actualizado exitosamente id={}", actualizado.getIdPersonal());
        return actualizado;
    }
     
    public Optional<Personal> findById(Long id) {
        logger.info("Buscando personal por id={}", id);
        return personalRepository.findById(id);
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de personal id={}", id);
        return personalRepository.existsById(id);
    }

    public void eliminar(Long id) {
        logger.info("Eliminando personal id={}", id);
        
        // MEJORA: Regla de negocio que valida existencia antes de borrar
        if (!personalRepository.existsById(id)) {
            logger.warn("No se puede eliminar: Miembro del personal no encontrado id={}", id);
            throw new ResourceNotFoundException("No se puede eliminar: Personal no encontrado con el ID: " + id);
        }
        
        personalRepository.deleteById(id);
        logger.info("Personal eliminado exitosamente id={}", id);
    }
}