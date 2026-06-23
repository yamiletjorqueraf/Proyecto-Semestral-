package cl.duoc.ms_dueno.controller;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_dueno.model.Dueno;
import cl.duoc.ms_dueno.service.DuenoService;
import cl.duoc.ms_dueno.assamblers.DuenoModelAssembler;
import cl.duoc.ms_dueno.dto.DuenoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/dueno")
@Tag(name = "Dueños V2", description = "Versión 2 - Consultas con HATEOAS")
public class DuenoControllerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(DuenoControllerV2.class);
 
    private final DuenoService duenoService;
    private final DuenoModelAssembler assembler;
 
    public DuenoControllerV2(DuenoService duenoService, DuenoModelAssembler assembler) {
        this.duenoService = duenoService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar dueños V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<EntityModel<DuenoDTO>> listarDuenos() {
        logger.info("V2 GET /api/v2/dueno - Listando dueños");
        List<EntityModel<DuenoDTO>> duenos = duenoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(duenos,
                linkTo(methodOn(DuenoControllerV2.class).listarDuenos()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener dueño por ID V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dueño encontrado"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public EntityModel<DuenoDTO> obtenerDueno(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/dueno/{} - Obteniendo dueño", id);
        Dueno dueno = duenoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Dueño no encontrado"));
        return assembler.toModel(dueno);
    }
}