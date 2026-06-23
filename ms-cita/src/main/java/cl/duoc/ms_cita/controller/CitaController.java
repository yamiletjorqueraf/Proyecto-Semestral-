package cl.duoc.ms_cita.controller;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_cita.model.Cita;
import cl.duoc.ms_cita.service.CitaService;
import cl.duoc.ms_cita.assamblers.CitaModelAssembler;
import cl.duoc.ms_cita.dto.CitaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/cita")
@Tag(name = "Citas", description = "Operaciones del microservicio de citas")
public class CitaController {
 
    private static final Logger logger = LoggerFactory.getLogger(CitaController.class);
 
    private final CitaService citaService;
    private final CitaModelAssembler assembler;
 
    public CitaController(CitaService citaService, CitaModelAssembler assembler) {
        this.citaService = citaService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear cita")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cita creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Mascota o personal no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<CitaDTO>> crearCita(@RequestBody CitaDTO citaDto) {
        logger.info("POST /api/v1/cita - Solicitud recibida");
        Cita nueva = citaService.guardar(citaDto.toModel());
        logger.info("Cita creada con id={}", nueva.getIdCita());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }
 
    @GetMapping
    @Operation(summary = "Listar citas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de citas retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<CitaDTO>>> listarCitas() {
        logger.info("GET /api/v1/cita - Solicitud recibida");
        List<EntityModel<CitaDTO>> citas = citaService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        CollectionModel<EntityModel<CitaDTO>> collection = CollectionModel.of(citas,
                linkTo(methodOn(CitaController.class).listarCitas()).withSelfRel());
        logger.info("Total citas retornadas: {}", citas.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe cita")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Verificación exitosa"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> existeCita(@PathVariable Long id) {
        logger.info("GET /api/v1/cita/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(citaService.existePorId(id));
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita encontrada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<CitaDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/cita/{} - Solicitud recibida", id);
        return citaService.findById(id)
                .map(c -> { logger.info("Cita retornada id={}", id);
                    return ResponseEntity.ok(assembler.toModel(c)); })
                .orElseGet(() -> { logger.warn("Cita no encontrada id={}", id);
                    return ResponseEntity.notFound().build(); });
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
        @ApiResponse(responseCode = "400", description = "Mascota o personal no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<CitaDTO>> actualizar(@PathVariable Long id, @RequestBody CitaDTO dto) {
        logger.info("PUT /api/v1/cita/{} - Solicitud recibida", id);
        Cita actualizada = citaService.actualizar(id, dto.toModel());
        logger.info("Cita actualizada id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/cita/{} - Solicitud recibida", id);
        citaService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(CitaController.class).listarCitas()).withRel("todas-las-citas"));
        logger.info("Cita eliminada id={}", id);
        return ResponseEntity.ok(model);
    }
}
 