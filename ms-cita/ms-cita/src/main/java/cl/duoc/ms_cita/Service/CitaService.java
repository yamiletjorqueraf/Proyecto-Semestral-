package cl.duoc.ms_cita.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Repository.CitaRepository;

@Service
public class CitaService {
    private final CitaRepository citaRepository;

     // TODO: agregar MascotaClient y PersonalClient cuando esos ms estén listos
    // private final MascotaClient mascotaClient;
    // private final PersonalClient personalClient;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public Cita guardar(Cita cita) {
        // TODO: validar con Feign cuando ms-mascota y ms-personal estén listos
        // validarRelaciones(cita.getIdMascota(), cita.getIdPersonal());
        return citaRepository.save(cita);
    }

    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public boolean existePorId(Long id) {
        return citaRepository.existsById(id);
    }

    public Cita actualizar(Long id, Cita datosNuevos) {
        Cita existente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
 
        // TODO: validar con Feign cuando ms-mascota y ms-personal estén listos
        // validarRelaciones(datosNuevos.getIdMascota(), datosNuevos.getIdPersonal());
 
        existente.setIdMascota(datosNuevos.getIdMascota());
        existente.setIdPersonal(datosNuevos.getIdPersonal());
        existente.setFechaHora(datosNuevos.getFechaHora());
        existente.setMotivo(datosNuevos.getMotivo());
        existente.setEstado(datosNuevos.getEstado());
 
        return citaRepository.save(existente);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    // TODO: descomentar cuando ms-mascota y ms-personal estén listos
    // private void validarRelaciones(Long idMascota, Long idPersonal) {
    //     Boolean mascotaExiste = mascotaClient.existeMascota(idMascota);
    //     if (mascotaExiste == null || !mascotaExiste) {
    //         throw new RuntimeException("La mascota con id " + idMascota + " no existe");
    //     }
    //     Boolean personalExiste = personalClient.existePersonal(idPersonal);
    //     if (personalExiste == null || !personalExiste) {
    //         throw new RuntimeException("El personal con id " + idPersonal + " no existe");
    //     }
    // }

}
