package cl.duoc.ms_mascota.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import cl.duoc.ms_mascota.client.DuenoClient;
import cl.duoc.ms_mascota.exception.BadRequestException;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.repository.MascotaRepository;

@Service
public class MascotaService {

        private static final Logger logger = LoggerFactory.getLogger(MascotaService.class);

       private final MascotaRepository mascotaRepository;
        private final DuenoClient duenoClient;

    public MascotaService(MascotaRepository mascotaRepository, DuenoClient duenoClient) {
        this.mascotaRepository = mascotaRepository;
        this.duenoClient = duenoClient;
    }

    public List<Mascota> listar() {
        logger.info("Listando todas las mascotas");
        List<Mascota> mascotas = mascotaRepository.findAll();
        logger.info("Total mascotas encontradas: {}", mascotas.size());
        return mascotas;
    }

    public Mascota guardar(Mascota mascota) {
        logger.info("Guardando mascota: nombre={}, idDueno={}", mascota.getNombre(), mascota.getIdDueno());
        logger.info("Validando existencia de dueño id={}", mascota.getIdDueno());
        if (!Boolean.TRUE.equals(duenoClient.existeDueno(mascota.getIdDueno()))) {
            logger.warn("Dueño no existe id={}", mascota.getIdDueno());
            throw new BadRequestException("El dueño con ID " + mascota.getIdDueno() + " no existe.");
        }
        Mascota guardada = mascotaRepository.save(mascota);
        logger.info("Mascota guardada exitosamente con id={}", guardada.getIdMascota());
        return guardada;
    }


    public Mascota actualizar(Long id, Mascota datosNuevos) {
        logger.info("Actualizando mascota id={}", id);
        Mascota existente = mascotaRepository.findById(id)
            .orElseThrow(() -> { 
                logger.warn("Mascota no encontrda para actualizar id={}", id);
                return new BadRequestException("Mascota no encontrada");
            });

        logger.info("Validando existencia de dueño id={}", datosNuevos.getIdDueno());
        if (!Boolean.TRUE.equals(duenoClient.existeDueno(datosNuevos.getIdDueno()))) {
            logger.warn("Dueño no encontrado para actualizar mascota idDueno={}", datosNuevos.getIdDueno());
            throw new BadRequestException("El dueño con ID " + datosNuevos.getIdDueno() + " no existe.");
        }

    existente.setNombre(datosNuevos.getNombre());
    existente.setEspecie(datosNuevos.getEspecie());
    existente.setRaza(datosNuevos.getRaza());
    existente.setEdad(datosNuevos.getEdad());
    existente.setIdDueno(datosNuevos.getIdDueno());

    Mascota actualizada = mascotaRepository.save(existente);
    logger.info("Mascota actualizada exitosamente id={}", actualizada.getIdMascota());
    return actualizada;
}

    public Optional<Mascota> findById(Long id) {
        logger.info("Buscando mascota por id={}", id);
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isPresent()) {
            logger.info("Mascota encontrada id={}", id);
        } else {
            logger.warn("Mascota no encontrada id={}", id);
        }
        return mascota;
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de mascota id={}", id);
        boolean existe = mascotaRepository.existsById(id);
        logger.info("Mascota id={} existe={}", id, existe);
        return existe;
    }

    public void eliminar(Long id) {
        logger.info("Eliminando mascota id={}", id);
        mascotaRepository.deleteById(id);
        logger.info("Mascota eliminada id={}", id);
    }
}
