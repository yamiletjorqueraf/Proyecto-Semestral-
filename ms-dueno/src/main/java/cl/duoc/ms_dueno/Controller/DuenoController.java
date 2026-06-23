package cl.duoc.ms_dueno.Controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_dueno.Model.Dueno;
import cl.duoc.ms_dueno.Service.DuenoService;
import cl.duoc.ms_dueno.assamblers.DuenoModelAssembler;
import cl.duoc.ms_dueno.dto.DuenoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/dueno")
@Tag(name = "Dueños", description = "Operaciones del microservicio de dueños")
public class DuenoController {
 
    private static final Logger logger = LoggerFactory.getLogger(DuenoController.class);
 
    private final DuenoService duenoService;
    private final DuenoModelAssembler assembler;
 
    public DuenoController(DuenoService duenoService, DuenoModelAssembler assembler) {
        this.duenoService = duenoService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear dueño")
    public ResponseEntity<EntityModel<DuenoDTO>> crearDueno(@RequestBody DuenoDTO duenoDto) {
        logger.info("POST /api/v1/dueno - Solicitud recibida");
        Dueno nuevo = duenoService.guardar(duenoDto.toModel());
        logger.info("Dueño creado con id={}", nuevo.getIdDueno());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }
 
    @GetMapping
    @Operation(summary = "Listar dueños")
    public ResponseEntity<CollectionModel<EntityModel<DuenoDTO>>> listarUsuarios() {
        logger.info("GET /api/v1/dueno - Solicitud recibida");
        List<EntityModel<DuenoDTO>> duenos = duenoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        CollectionModel<EntityModel<DuenoDTO>> collection = CollectionModel.of(duenos,
                linkTo(methodOn(DuenoController.class).listarUsuarios()).withSelfRel());
        logger.info("Total dueños retornados: {}", duenos.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe un dueño")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        logger.info("GET /api/v1/dueno/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(duenoService.existePorId(id));
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener dueño por ID")
    public ResponseEntity<EntityModel<DuenoDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/dueno/{} - Solicitud recibida", id);
        return duenoService.findById(id)
                .map(d -> { logger.info("Dueño retornado id={}", id);
                    return ResponseEntity.ok(assembler.toModel(d)); })
                .orElseGet(() -> { logger.warn("Dueño no encontrado id={}", id);
                    return ResponseEntity.notFound().build(); });
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dueño")
    public ResponseEntity<EntityModel<DuenoDTO>> actualizar(@PathVariable Long id, @RequestBody DuenoDTO dto) {
        logger.info("PUT /api/v1/dueno/{} - Solicitud recibida", id);
        Dueno actualizado = duenoService.actualizar(id, dto.toModel());
        logger.info("Dueño actualizado id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dueño")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/dueno/{} - Solicitud recibida", id);
        duenoService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(DuenoController.class).listarUsuarios()).withRel("todos-los-duenos"));
        logger.info("Dueño eliminado id={}", id);
        return ResponseEntity.ok(model);
    }
}
