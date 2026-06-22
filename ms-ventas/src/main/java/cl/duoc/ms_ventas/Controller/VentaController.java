package cl.duoc.ms_ventas.Controller;

import cl.duoc.ms_ventas.Model.Venta;
import cl.duoc.ms_ventas.Service.VentaService;
import cl.duoc.ms_ventas.assamblers.VentaModelAssembler;
import cl.duoc.ms_ventas.dto.VentaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones del microservicio de ventas")
public class VentaController {

    private static final Logger logger = LoggerFactory.getLogger(VentaController.class);
 
    private final VentaService ventaService;
    private final VentaModelAssembler assembler;
 
    public VentaController(VentaService ventaService, VentaModelAssembler assembler) {
        this.ventaService = ventaService;
        this.assembler = assembler;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva venta", description = "Registra una venta validando dueño y mascota")
    public ResponseEntity<EntityModel<VentaDTO>> guardar(@Valid @RequestBody VentaDTO dto) {
        logger.info("POST /api/v1/ventas - Solicitud recibida");
        Venta venta = ventaService.guardar(dto.toModel());
        logger.info("Venta creada con id={}", venta.getIdVenta());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(venta));
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas", description = "Retorna la lista completa de ventas")
    public ResponseEntity<CollectionModel<EntityModel<VentaDTO>>> listar() {
        logger.info("GET /api/v1/ventas - Solicitud recibida");
        List<EntityModel<VentaDTO>> ventas = ventaService.listar().stream()
                .map(assembler::toModel)
                .toList();
        CollectionModel<EntityModel<VentaDTO>> collection = CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).listar()).withSelfRel()
        );
        logger.info("Total ventas retornadas: {}", ventas.size());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Retorna una venta específica por su ID")
    public ResponseEntity<EntityModel<VentaDTO>> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/ventas/{} - Solicitud recibida", id);
        Venta venta = ventaService.obtenerPorId(id);
        logger.info("Venta retornada id={}", id);
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si existe una venta", description = "Usado por ms-pago para validar la venta")
    public ResponseEntity<Boolean> existeVenta(@PathVariable Long id) {
        logger.info("GET /api/v1/ventas/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(ventaService.existePorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una venta", description = "Actualiza los datos de una venta existente")
    public ResponseEntity<EntityModel<VentaDTO>> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody VentaDTO dto) {
        logger.info("PUT /api/v1/ventas/{} - Solicitud recibida", id);
        Venta venta = ventaService.actualizar(id, dto.toModel());
        logger.info("Venta actualizada id={}", id);
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta", description = "Elimina una venta por su ID")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/ventas/{} - Solicitud recibida", id);
        ventaService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(VentaController.class).listar()).withRel("todas-las-ventas")
        );
        logger.info("Venta eliminada id={}", id);
        return ResponseEntity.ok(model);
    }

}
