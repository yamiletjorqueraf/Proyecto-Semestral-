package cl.duoc.ms_personal.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        Personal existente = personalRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Personal no encontrado para actualizar id={}", id);
                return new RuntimeException("Miembro del personal no encontrado");
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
        Optional<Personal> personal = personalRepository.findById(id);
        if (personal.isPresent()) {
            logger.info("Personal encontrado id={}", id);
        } else {
            logger.warn("Personal no encontrado id={}", id);
        }
        return personal;
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de personal id={}", id);
        boolean existe = personalRepository.existsById(id);
        logger.info("Personal id={} existe={}", id, existe);
        return existe;
    }

    public void eliminar(Long id) {
        logger.info("Eliminando personal id={}", id);
        personalRepository.deleteById(id);
        logger.info("Personal eliminado exitosamente id={}", id);
    }

 
}
