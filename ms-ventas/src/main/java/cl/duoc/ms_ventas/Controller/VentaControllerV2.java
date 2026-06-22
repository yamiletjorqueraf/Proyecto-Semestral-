package cl.duoc.ms_ventas.controller;

import cl.duoc.ms_ventas.assamblers.VentaModelAssembler;
import cl.duoc.ms_ventas.dto.VentaDTO;
import cl.duoc.ms_ventas.model.Venta;
import cl.duoc.ms_ventas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.stream.Collectors;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v2/ventas")
@Tag(name = "Ventas V2", description = "Versión 2 - Operaciones de consulta con HATEOAS")
public class VentaControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(VentaControllerV2.class);
 
    private final VentaService ventaService;
    private final VentaModelAssembler assembler;
 
    public VentaControllerV2(VentaService ventaService, VentaModelAssembler assembler) {
        this.ventaService = ventaService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas V2", description = "Retorna lista de ventas con links HATEOAS")
    public CollectionModel<EntityModel<VentaDTO>> listarVentas() {
        logger.info("V2 GET /api/v2/ventas - Listando ventas");
        List<EntityModel<VentaDTO>> ventas = ventaService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).listarVentas()).withSelfRel());
    }

     @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID V2", description = "Retorna una venta con links HATEOAS")
    public EntityModel<VentaDTO> obtenerVenta(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/ventas/{} - Obteniendo venta", id);
        Venta venta = ventaService.obtenerPorId(id);
        return assembler.toModel(venta);
    }

}
