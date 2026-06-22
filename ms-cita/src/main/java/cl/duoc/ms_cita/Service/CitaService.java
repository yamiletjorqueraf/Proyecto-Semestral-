package cl.duoc.ms_cita.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.duoc.ms_cita.Client.MascotaClient;
import cl.duoc.ms_cita.Client.PersonalClient;
import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Repository.CitaRepository;

@Service
public class CitaService {

    private static final Logger logger = LoggerFactory.getLogger(CitaService.class);

    private final CitaRepository citaRepository;
    private final MascotaClient mascotaClient;
    private final PersonalClient personalClient;


    public CitaService(CitaRepository citaRepository, MascotaClient mascotaClient, PersonalClient personalClient) {
        this.citaRepository = citaRepository;
        this.mascotaClient = mascotaClient;
        this.personalClient = personalClient;
    }

    public List<Cita> listar() {
        logger.info("Listando todas las citas");
        List<Cita> citas = citaRepository.findAll();
        logger.info("Total citas encontradas: {}", citas.size());
        return citas;
    }

    public Cita guardar(Cita cita) {
        logger.info("Guardando cita: idMascota={}, idPersonal={}", cita.getIdMascota(), cita.getIdPersonal());
        validarRelaciones(cita.getIdMascota(), cita.getIdPersonal());
        Cita guardada = citaRepository.save(cita);
        logger.info("Cita guardada exitosamente con id={}", guardada.getIdCita());
        return guardada;
    }

    public Optional<Cita> findById(Long id) {
        logger.info("Buscando cita por id={}", id);
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            logger.info("Cita encontrada id={}", id);
        } else {
            logger.warn("Cita no encontrada id={}", id);
        }
        return cita;
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de cita id={}", id);
        boolean existe = citaRepository.existsById(id);
        logger.info("Cita id={} existe={}", id, existe);
        return existe;
    }

    public Cita actualizar(Long id, Cita datosNuevos) {
        logger.info("Actualizando cita id={}", id);
        Cita existente = citaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cita no encontrada para actualizar id={}", id);
                    return new RuntimeException("Cita no encontrada con id: " + id);
                });
 
        validarRelaciones(datosNuevos.getIdMascota(), datosNuevos.getIdPersonal());
 
        existente.setIdMascota(datosNuevos.getIdMascota());
        existente.setIdPersonal(datosNuevos.getIdPersonal());
        existente.setFechaHora(datosNuevos.getFechaHora());
        existente.setMotivo(datosNuevos.getMotivo());
        existente.setEstado(datosNuevos.getEstado());
 
        Cita actualizada = citaRepository.save(existente);
        logger.info("Cita actualizada exitosamente id={}", actualizada.getIdCita());
        return actualizada;
    }

    public void eliminar(Long id) {
        logger.info("Eliminando cita id={}", id);
        citaRepository.deleteById(id);
        logger.info("Cita eliminada exitosamente id={}", id);
    }

    
     private void validarRelaciones(Long idMascota, Long idPersonal) {
        logger.info("Validando mascota id={}", idMascota);
        Boolean mascotaExiste = mascotaClient.existeMascota(idMascota);
        if (mascotaExiste == null || !mascotaExiste) {
            logger.warn("Mascota no existe id={}", idMascota);
            throw new RuntimeException("La mascota con id " + idMascota + " no existe");
        }
        logger.info("Validando personal id={}", idPersonal);
        Boolean personalExiste = personalClient.existePersonal(idPersonal);
        if (personalExiste == null || !personalExiste) {
            logger.warn("Personal no existe id={}", idPersonal);
            throw new RuntimeException("El personal con id " + idPersonal + " no existe");
        }
    }
     

}
