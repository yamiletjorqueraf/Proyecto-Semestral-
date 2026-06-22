package cl.duoc.ms_personal.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.repository.PersonalRepository;

@Service
public class PersonalService {
    private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public List<Personal> listar() {
        return personalRepository.findAll();
    }

    public Personal guardar(Personal personal) {
        return personalRepository.save(personal);
    }

    public Personal actualizar(Long id, Personal datosNuevos) {
        Personal existente = personalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Miembro del personal no encontrado"));
        
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setCargo(datosNuevos.getCargo());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setActivo(datosNuevos.isActivo());
        
        return personalRepository.save(existente);
    }
     
    public Optional<Personal> findById(Long id) {
    return personalRepository.findById(id);
}
    public boolean existePorId(Long id) {
        return personalRepository.existsById(id);
    }

    public void eliminar(Long id) {
        personalRepository.deleteById(id);
    }

 
}
