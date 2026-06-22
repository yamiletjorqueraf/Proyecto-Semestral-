package cl.duoc.ms_dueno.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import cl.duoc.ms_dueno.Client.MascotaClient;
import cl.duoc.ms_dueno.Client.UsuarioClient;
import cl.duoc.ms_dueno.Model.Dueno;
import cl.duoc.ms_dueno.Repository.DuenoRepository;

@Service
public class DuenoService {

    private static final Logger logger = LoggerFactory.getLogger(DuenoService.class);


    private final DuenoRepository duenoRepository;
    private final UsuarioClient usuarioClient;
    private final MascotaClient mascotaClient;

    public DuenoService(DuenoRepository duenoRepository,
                        UsuarioClient usuarioClient,
                        MascotaClient mascotaClient) {
		this.duenoRepository = duenoRepository;
        this.usuarioClient = usuarioClient;
        this.mascotaClient = mascotaClient;
        
	}

    public List<Dueno> listar() {
        logger.info("Listando todos los dueños");
        List<Dueno> duenos = duenoRepository.findAll();
        logger.info("Total dueños encontrados: {}", duenos.size());
        return duenos;
    }

    public Dueno actualizar(Long id, Dueno datosNuevos) {
        logger.info("Actualizando dueño id={}", id);
        Dueno existente = duenoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Dueño no encontrado para actualizar id={}", id);
                    return new RuntimeException("Dueño no encontrado");
                });
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setRut(datosNuevos.getRut());
        existente.setEmail(datosNuevos.getEmail());
        existente.setTelefono(datosNuevos.getTelefono());
        existente.setDireccion(datosNuevos.getDireccion());
        Dueno actualizado = duenoRepository.save(existente);
        logger.info("Dueño actualizado exitosamente id={}", actualizado.getIdDueno());
        return actualizado;
    }

    public Dueno guardar(Dueno dueno) {
        logger.info("Guardando dueño: nombre={}, rut={}", dueno.getNombre(), dueno.getRut());
        Dueno guardado = duenoRepository.save(dueno);
        logger.info("Dueño guardado exitosamente con id={}", guardado.getIdDueno());
        return guardado;
    }

    public Optional<Dueno> findById(Long id) {
        logger.info("Buscando dueño por id={}", id);
        Optional<Dueno> dueno = duenoRepository.findById(id);
        if (dueno.isPresent()) {
            logger.info("Dueño encontrado id={}", id);
        } else {
            logger.warn("Dueño no encontrado id={}", id);
        }
        return dueno;
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de dueño id={}", id);
        boolean existe = duenoRepository.existsById(id);
        logger.info("Dueño id={} existe={}", id, existe);
        return existe;
    }

    public void eliminar(Long id) {
        logger.info("Eliminando dueño id={}", id);
        duenoRepository.deleteById(id);
        logger.info("Dueño eliminado exitosamente id={}", id);
    }


}
