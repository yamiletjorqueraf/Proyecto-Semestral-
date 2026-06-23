package cl.duoc.ms_farmacia.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import cl.duoc.ms_farmacia.assamblers.MedicamentoModelAssembler;
import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.service.MedicamentoService;
import cl.duoc.ms_farmacia.exception.ResourceNotFoundException; // <-- 2. IMPORTACIÓN DE EXCEPCIÓN CORREGIDA
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/farmacia")
@Tag(name = "Medicamentos", description = "Operaciones del inventario de medicamentos de la veterinaria")
public class MedicamentoController {

    private static final Logger logger = LoggerFactory.getLogger(MedicamentoController.class);

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private MedicamentoModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Registrar medicamento", description = "Añade un nuevo medicamento al inventario de la farmacia")
    public ResponseEntity<EntityModel<MedicamentoDTO>> crearMedicamento(@Valid @RequestBody MedicamentoDTO dto) {
        logger.info("POST /api/v1/farmacia - Solicitud recibida");
        Medicamento nuevo = medicamentoService.guardar(dto);
        logger.info("Medicamento registrado con id={}", nuevo.getIdMedicamento());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listar inventario", description = "Retorna todos los medicamentos disponibles en la farmacia")
    public ResponseEntity<CollectionModel<EntityModel<MedicamentoDTO>>> listarMedicamentos() {
        logger.info("GET /api/v1/farmacia - Solicitud recibida");
        
        List<EntityModel<MedicamentoDTO>> medicamentos = medicamentoService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MedicamentoDTO>> collection = CollectionModel.of(medicamentos,
                linkTo(methodOn(MedicamentoController.class).listarMedicamentos()).withSelfRel());
                
        logger.info("Total de medicamentos retornados: {}", medicamentos.size());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener medicamento por ID")
    public ResponseEntity<EntityModel<MedicamentoDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/farmacia/{} - Solicitud recibida", id);
        return medicamentoService.findById(id)
                .map(m -> {
                    logger.info("Medicamento retornado id={}", id);
                    return ResponseEntity.ok(assembler.toModel(m));
                })
                .orElseThrow(() -> {
                    logger.warn("Medicamento no encontrado id={}", id);
                    return new ResourceNotFoundException("Medicamento no encontrado con ID: " + id);
                });
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar medicamento")
    // 3. TIPO DE DTO CORREGIDO (MedicamentoDTO en lugar de FarmaciaDTO)
    public ResponseEntity<EntityModel<MedicamentoDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody MedicamentoDTO dto) {
        logger.info("PUT /api/v1/farmacia/{} - Solicitud recibida", id);
        
        Medicamento datosModificar = new Medicamento();
        datosModificar.setNombre(dto.getNombre());
        datosModificar.setDescripcion(dto.getDescripcion());
        datosModificar.setPrecio(dto.getPrecio());
        datosModificar.setStock(dto.getStock());

        Medicamento actualizado = medicamentoService.actualizar(id, datosModificar);
        logger.info("Medicamento modificado exitosamente id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar medicamento")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/farmacia/{} - Solicitud recibida", id);
        medicamentoService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(MedicamentoController.class).listarMedicamentos()).withRel("inventario-completo"));
        logger.info("Medicamento eliminado del sistema id={}", id);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar existencia en inventario")
    public ResponseEntity<Boolean> existeMedicamento(@PathVariable Long id) {
        logger.info("GET /api/v1/farmacia/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(medicamentoService.existePorId(id));
    }
}