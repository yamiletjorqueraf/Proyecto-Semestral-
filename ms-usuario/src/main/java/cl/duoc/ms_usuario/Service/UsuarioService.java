package cl.duoc.ms_usuario.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Repository.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

    public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

    public Usuario actualizar (Long id, Usuario datosNuevos) {
        Usuario existente = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setRol(datosNuevos.getRol());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setTelefono(datosNuevos.getTelefono());
        return usuarioRepository.save(existente);
    }

    public Usuario guardar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }



}
