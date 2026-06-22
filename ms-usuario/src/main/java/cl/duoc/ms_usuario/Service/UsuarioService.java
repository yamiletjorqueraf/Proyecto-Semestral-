package cl.duoc.ms_usuario.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Repository.UsuarioRepository;


@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

    public List<Usuario> listar() {
        logger.info("Listando todos los usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("Total usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    public Usuario actualizar(Long id, Usuario datosNuevos) {
        logger.info("Actualizando usuario id={}", id);
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado para actualizar id={}", id);
                    return new RuntimeException("Usuario no encontrado");
                });
        existente.setNombre(datosNuevos.getNombre());
        existente.setApellido(datosNuevos.getApellido());
        existente.setRol(datosNuevos.getRol());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setTelefono(datosNuevos.getTelefono());
        Usuario actualizado = usuarioRepository.save(existente);
        logger.info("Usuario actualizado exitosamente id={}", actualizado.getIdUsuario());
        return actualizado;
    }

    public Usuario guardar(Usuario usuario) {
        logger.info("Guardando usuario: nombre={}, correo={}", usuario.getNombre(), usuario.getCorreo());
        Usuario guardado = usuarioRepository.save(usuario);
        logger.info("Usuario guardado exitosamente con id={}", guardado.getIdUsuario());
        return guardado;
    }

    public Optional<Usuario> findById(Long id) {
        logger.info("Buscando usuario por id={}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            logger.info("Usuario encontrado id={}", id);
        } else {
            logger.warn("Usuario no encontrado id={}", id);
        }
        return usuario;
    }

    public boolean existePorId(Long id) {
        logger.info("Verificando existencia de usuario id={}", id);
        boolean existe = usuarioRepository.existsById(id);
        logger.info("Usuario id={} existe={}", id, existe);
        return existe;
    }

    public void eliminar(Long id) {
        logger.info("Eliminando usuario id={}", id);
        usuarioRepository.deleteById(id);
        logger.info("Usuario eliminado exitosamente id={}", id);
    }



}
