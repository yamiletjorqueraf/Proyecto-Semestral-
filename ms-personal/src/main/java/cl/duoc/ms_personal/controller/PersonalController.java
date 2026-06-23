package cl.duoc.ms_personal.controller;
 
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
 
import cl.duoc.ms_personal.assamblers.PersonalModelAssembler;
import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.service.PersonalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/personal")
@Tag(name = "Personal", description = "Operaciones del microservicio de personal")
public class PersonalController {
 
    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);
 
    private final PersonalService personalService;
    private final PersonalModelAssembler assembler;
 
    public PersonalController(PersonalService personalService, PersonalModelAssembler assembler) {
        this.personalService = personalService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear personal")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Personal creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PersonalDTO>> crearPersonal(@RequestBody PersonalDTO dto) {
        logger.info("POST /api/v1/personal - Solicitud recibida");
        Personal nuevo = personalService.guardar(dto.toModel());
        logger.info("Personal creado con id={}", nuevo.getIdPersonal());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }
 
    @GetMapping
    @Operation(summary = "Listar personal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de personal retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<PersonalDTO>>> listarPersonal() {
        logger.info("GET /api/v1/personal - Solicitud recibida");
        List<EntityModel<PersonalDTO>> lista = personalService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        CollectionModel<EntityModel<PersonalDTO>> collection = CollectionModel.of(lista,
                linkTo(methodOn(PersonalController.class).listarPersonal()).withSelfRel());
        logger.info("Total personal retornado: {}", lista.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe personal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Verificación exitosa"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> existePersonal(@PathVariable Long id) {
        logger.info("GET /api/v1/personal/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(personalService.existePorId(id));
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener personal por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Personal encontrado"),
        @ApiResponse(responseCode = "404", description = "Personal no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PersonalDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/personal/{} - Solicitud recibida", id);
        Optional<Personal> personal = personalService.findById(id);
        if (personal.isPresent()) {
            logger.info("Personal retornado id={}", id);
            return ResponseEntity.ok(assembler.toModel(personal.get()));
        }
        logger.warn("Personal no encontrado id={}", id);
        return ResponseEntity.notFound().build();
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar personal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Personal actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Personal no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PersonalDTO>> actualizar(@PathVariable Long id, @RequestBody PersonalDTO dto) {
        logger.info("PUT /api/v1/personal/{} - Solicitud recibida", id);
        Personal actualizado = personalService.actualizar(id, dto.toModel());
        logger.info("Personal actualizado id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar personal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Personal eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Personal no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/personal/{} - Solicitud recibida", id);
        personalService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(PersonalController.class).listarPersonal()).withRel("todo-el-personal"));
        logger.info("Personal eliminado id={}", id);
        return ResponseEntity.ok(model);
    }
}