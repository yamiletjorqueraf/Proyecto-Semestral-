package cl.duoc.ms_cita.Controller;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Service.CitaService;
import cl.duoc.ms_cita.assamblers.CitaModelAssembler;
import cl.duoc.ms_cita.dto.CitaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/cita")
@Tag(name = "Citas V2", description = "Versión 2 - Consultas con HATEOAS")
public class CitaControllerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(CitaControllerV2.class);
 
    private final CitaService citaService;
    private final CitaModelAssembler assembler;
 
    public CitaControllerV2(CitaService citaService, CitaModelAssembler assembler) {
        this.citaService = citaService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar citas V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<EntityModel<CitaDTO>> listarCitas() {
        logger.info("V2 GET /api/v2/cita - Listando citas");
        List<EntityModel<CitaDTO>> citas = citaService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(citas,
                linkTo(methodOn(CitaControllerV2.class).listarCitas()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita encontrada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public EntityModel<CitaDTO> obtenerCita(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/cita/{} - Obteniendo cita", id);
        Cita cita = citaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return assembler.toModel(cita);
    }
}