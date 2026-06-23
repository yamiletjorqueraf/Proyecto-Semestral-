package cl.duoc.ms_pago.controller;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_pago.dto.PagoDTO;
import cl.duoc.ms_pago.model.Pago;
import cl.duoc.ms_pago.assamblers.PagoModelAssembler;
import cl.duoc.ms_pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/pagos")
@Tag(name = "Pagos V2", description = "Versión 2 - Operaciones de consulta con HATEOAS")
public class PagoControllerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(PagoControllerV2.class);
 
    private final PagoService pagoService;
    private final PagoModelAssembler assembler;
 
    public PagoControllerV2(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar todos los pagos V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<EntityModel<PagoDTO>> listarPagos() {
        logger.info("V2 GET /api/v2/pagos - Listando pagos");
        List<EntityModel<PagoDTO>> pagos = pagoService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).listarPagos()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID V2")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public EntityModel<PagoDTO> obtenerPago(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/pagos/{} - Obteniendo pago", id);
        Pago pago = pagoService.obtenerPorId(id);
        return assembler.toModel(pago);
    }
}