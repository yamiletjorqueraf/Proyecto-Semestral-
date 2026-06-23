package cl.duoc.ms_pago.Controller;
 
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_pago.dto.PagoDTO;
import cl.duoc.ms_pago.Model.Pago;
import cl.duoc.ms_pago.assamblers.PagoModelAssembler;
import cl.duoc.ms_pago.Service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones del microservicio de pagos")
public class PagoController {
 
    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
 
    private final PagoService pagoService;
    private final PagoModelAssembler assembler;
 
    public PagoController(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }
 
    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Registra un pago validando la venta y el dueño")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Venta o dueño no existe"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PagoDTO>> guardar(@Valid @RequestBody PagoDTO dto) {
        logger.info("POST /api/v1/pagos - Solicitud recibida");
        Pago pago = pagoService.guardar(dto.toModel());
        logger.info("Pago creado con id={}", pago.getIdPago());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(pago));
    }
 
    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Retorna la lista completa de pagos registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pagos retornada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> listar() {
        logger.info("GET /api/v1/pagos - Solicitud recibida");
        List<EntityModel<PagoDTO>> pagos = pagoService.listar().stream()
                .map(assembler::toModel).toList();
        CollectionModel<EntityModel<PagoDTO>> collection = CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).listar()).withSelfRel());
        logger.info("Total pagos retornados: {}", pagos.size());
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PagoDTO>> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/pagos/{} - Solicitud recibida", id);
        Pago pago = pagoService.obtenerPorId(id);
        logger.info("Pago retornado id={}", id);
        return ResponseEntity.ok(assembler.toModel(pago));
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "400", description = "Venta o dueño no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<PagoDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        logger.info("PUT /api/v1/pagos/{} - Solicitud recibida", id);
        Pago pago = pagoService.actualizar(id, dto.toModel());
        logger.info("Pago actualizado id={}", id);
        return ResponseEntity.ok(assembler.toModel(pago));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/pagos/{} - Solicitud recibida", id);
        pagoService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(PagoController.class).listar()).withRel("todos-los-pagos"));
        logger.info("Pago eliminado id={}", id);
        return ResponseEntity.ok(model);
    }
}