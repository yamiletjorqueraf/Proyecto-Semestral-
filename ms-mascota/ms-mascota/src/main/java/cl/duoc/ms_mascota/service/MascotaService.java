package cl.duoc.ms_mascota.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.ms_mascota.client.DuenoClient;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.repository.MascotaRepository;

@Service
public class MascotaService {
       private final MascotaRepository mascotaRepository;
    private final DuenoClient duenoClient;

    public MascotaService(MascotaRepository mascotaRepository, DuenoClient duenoClient) {
        this.mascotaRepository = mascotaRepository;
        this.duenoClient = duenoClient;
    }

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota guardar(Mascota mascota) {
        // Validación de negocio cruzada usando Feign
        if (!duenoClient.existeDueno(mascota.getIdDueno())) {
            throw new RuntimeException("No se puede registrar mascota: El Dueño con ID " + mascota.getIdDueno() + " no existe.");
        }
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota datosNuevos) {
        Mascota existente = mascotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        
        if (!duenoClient.existeDueno(datosNuevos.getIdDueno())) {
            throw new RuntimeException("Dueño no encontrado para actualizar mascota");
        }

        existente.setNombre(datosNuevos.getNombre());
        existente.setEspecie(datosNuevos.getEspecie());
        existente.setRaza(datosNuevos.getRaza());
        existente.setEdad(datosNuevos.getEdad());
        existente.setIdDueno(datosNuevos.getIdDueno());
        
        return mascotaRepository.save(existente);
    }

    public boolean existePorId(Long id) {
        return mascotaRepository.existsById(id);
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }
}
