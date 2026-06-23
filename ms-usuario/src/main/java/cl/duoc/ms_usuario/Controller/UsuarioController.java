package cl.duoc.ms_usuario.Controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.ms_usuario.Assamblers.UsuarioModelAssembler;
import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Service.UsuarioService;

import cl.duoc.ms_usuario.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuarios", description = "Operaciones del microservicio de usuarios")
public class UsuarioController {
 
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
 
    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
 
    public UsuarioController(UsuarioService usuarioService, UsuarioModelAssembler assembler) {
        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    public ResponseEntity<EntityModel<UsuarioDTO>> crearUsuario(@RequestBody UsuarioDTO usuarioDto) {
        logger.info("POST /api/v1/usuario - Solicitud recibida");
        Usuario nuevo = usuarioService.guardar(usuarioDto.toModel());
        logger.info("Usuario creado con id={}", nuevo.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }
 
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> listarUsuarios() {
        logger.info("GET /api/v1/usuario - Solicitud recibida");
        List<EntityModel<UsuarioDTO>> usuarios = usuarioService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<UsuarioDTO>> collection = CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withSelfRel());
        logger.info("Total usuarios retornados: {}", usuarios.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe un usuario")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        logger.info("GET /api/v1/usuario/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(usuarioService.existePorId(id));
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<EntityModel<UsuarioDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/usuario/{} - Solicitud recibida", id);
        return usuarioService.findById(id)
                .map(u -> {
                    logger.info("Usuario retornado id={}", id);
                    return ResponseEntity.ok(assembler.toModel(u));
                })
                .orElseGet(() -> {
                    logger.warn("Usuario no encontrado id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        logger.info("PUT /api/v1/usuario/{} - Solicitud recibida", id);
        Usuario actualizado = usuarioService.actualizar(id, dto.toModel());
        logger.info("Usuario actualizado id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/usuario/{} - Solicitud recibida", id);
        usuarioService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("todos-los-usuarios"));
        logger.info("Usuario eliminado id={}", id);
        return ResponseEntity.ok(model);
    }

}
