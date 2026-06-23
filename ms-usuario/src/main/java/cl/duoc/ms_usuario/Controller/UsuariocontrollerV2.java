package cl.duoc.ms_usuario.Controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import cl.duoc.ms_usuario.Assamblers.UsuarioModelAssembler;



import cl.duoc.ms_usuario.dto.UsuarioDTO;
import cl.duoc.ms_usuario.model.Usuario;
import cl.duoc.ms_usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/usuario")
@Tag(name = "Usuarios V2", description = "Versión 2 - Consultas con HATEOAS")
public class UsuariocontrollerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(UsuariocontrollerV2.class);
 
    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
 
    public UsuariocontrollerV2(UsuarioService usuarioService, UsuarioModelAssembler assembler) {
        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar usuarios V2")
    public CollectionModel<EntityModel<UsuarioDTO>> listarUsuarios() {
        logger.info("V2 GET /api/v2/usuario - Listando usuarios");
        List<EntityModel<UsuarioDTO>> usuarios = usuarioService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariocontrollerV2.class).listarUsuarios()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID V2")
    public EntityModel<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/usuario/{} - Obteniendo usuario", id);
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return assembler.toModel(usuario);
    }
}
