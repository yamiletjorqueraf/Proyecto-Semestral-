package cl.duoc.ms_usuario.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Service.UsuarioService;
import cl.duoc.ms_usuario.dto.UsuarioDTO;


@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDto) {
        logger.info("POST /api/v1/usuario - Solicitud recibida");
        Usuario nuevo = usuarioService.guardar(usuarioDto.toModel());
        logger.info("Usuario creado con id={}", nuevo.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.fromModel(nuevo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        logger.info("GET /api/v1/usuario - Solicitud recibida");
        List<Usuario> usuarios = usuarioService.listar();
        List<UsuarioDTO> dtos = usuarios.stream().map(UsuarioDTO::fromModel).collect(Collectors.toList());
        logger.info("Total usuarios retornados: {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        logger.info("GET /api/v1/usuario/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(usuarioService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/usuario/{} - Solicitud recibida", id);
        return usuarioService.findById(id)
                .map(u -> {
                    logger.info("Usuario retornado id={}", id);
                    return ResponseEntity.ok(UsuarioDTO.fromModel(u));
                })
                .orElseGet(() -> {
                    logger.warn("Usuario no encontrado id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        logger.info("PUT /api/v1/usuario/{} - Solicitud recibida", id);
        Usuario actualizado = usuarioService.actualizar(id, dto.toModel());
        logger.info("Usuario actualizado id={}", id);
        return ResponseEntity.ok(UsuarioDTO.fromModel(actualizado));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/usuario/{} - Solicitud recibida", id);
        usuarioService.eliminar(id);
        logger.info("Usuario eliminado id={}", id);
        return ResponseEntity.noContent().build();
    }

}
