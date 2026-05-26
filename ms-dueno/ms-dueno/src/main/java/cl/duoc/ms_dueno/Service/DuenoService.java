package cl.duoc.ms_dueno.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

//import cl.duoc.ms_dueno.Client.MascotaClient;
import cl.duoc.ms_dueno.Client.UsuarioClient;
import cl.duoc.ms_dueno.Model.Dueno;
import cl.duoc.ms_dueno.Repository.DuenoRepository;

@Service
public class DuenoService {
    private final DuenoRepository duenoRepository;
    private final UsuarioClient usuarioClient;
    //private final MascotaClient mascotaClient;

    public DuenoService(DuenoRepository duenoRepository,
                        UsuarioClient usuarioClient) {
		this.duenoRepository = duenoRepository;
        this.usuarioClient = usuarioClient;
        //this.mascotaClient = mascotaClient;
	}

    public List<Dueno> listar() {
		return duenoRepository.findAll();
	}

    public Dueno actualizar (Long id, Dueno datosNuevos) {
        Dueno existente = duenoRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setRut(datosNuevos.getRut());
        existente.setEmail(datosNuevos.getEmail());
        existente.setTelefono(datosNuevos.getTelefono());
        existente.setDireccion(datosNuevos.getDireccion());
        return duenoRepository.save(existente);
    }

    public Dueno guardar(Dueno dueno) {
		return duenoRepository.save(dueno);
	}

    public Optional<Dueno> findById(Long id) {
        return duenoRepository.findById(id);
    }

    public boolean existePorId(Long id) {
        return duenoRepository.existsById(id);
    }

    public void eliminar(Long id) {
        duenoRepository.deleteById(id);
    }


}
