package cl.duoc.ms_mascota.controller;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_mascota.assamblers.MascotaModelAssembler;
import cl.duoc.ms_mascota.dto.MascotaDTO;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/mascota")
@Tag(name = "Mascotas", description = "Operaciones del microservicio de mascotas")
public class MascotaController {
 
    private static final Logger logger = LoggerFactory.getLogger(MascotaController.class);
 
    private final MascotaService mascotaService;
    private final MascotaModelAssembler assembler;
 
    public MascotaController(MascotaService mascotaService, MascotaModelAssembler assembler) {
        this.mascotaService = mascotaService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Dueño no existe o datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<MascotaDTO>> crearMascota(@RequestBody MascotaDTO dto) {
        logger.info("POST /api/v1/mascota - Solicitud recibida");
        Mascota nueva = mascotaService.guardar(dto.toModel());
        logger.info("Mascota creada con id={}", nueva.getIdMascota());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }
 
    @GetMapping
    @Operation(summary = "Listar mascotas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mascotas retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<MascotaDTO>>> listarMascotas() {
        logger.info("GET /api/v1/mascota - Solicitud recibida");
        List<EntityModel<MascotaDTO>> mascotas = mascotaService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        CollectionModel<EntityModel<MascotaDTO>> collection = CollectionModel.of(mascotas,
                linkTo(methodOn(MascotaController.class).listarMascotas()).withSelfRel());
        logger.info("Total mascotas retornadas: {}", mascotas.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Verificación exitosa"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> existeMascota(@PathVariable Long id) {
        logger.info("GET /api/v1/mascota/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(mascotaService.existePorId(id));
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<MascotaDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/mascota/{} - Solicitud recibida", id);
        Optional<Mascota> mascota = mascotaService.findById(id);
        if (mascota.isPresent()) {
            logger.info("Mascota retornada id={}", id);
            return ResponseEntity.ok(assembler.toModel(mascota.get()));
        }
        logger.warn("Mascota no encontrada id={}", id);
        return ResponseEntity.notFound().build();
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "400", description = "Dueño no existe o datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<MascotaDTO>> actualizar(@PathVariable Long id, @RequestBody MascotaDTO dto) {
        logger.info("PUT /api/v1/mascota/{} - Solicitud recibida", id);
        Mascota actualizada = mascotaService.actualizar(id, dto.toModel());
        logger.info("Mascota actualizada id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/mascota/{} - Solicitud recibida", id);
        mascotaService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(MascotaController.class).listarMascotas()).withRel("todas-las-mascotas"));
        logger.info("Mascota eliminada id={}", id);
        return ResponseEntity.ok(model);
    }
}