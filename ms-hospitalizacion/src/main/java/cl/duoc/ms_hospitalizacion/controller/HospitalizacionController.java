package cl.duoc.ms_hospitalizacion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import cl.duoc.ms_hospitalizacion.assamblers.HospitalizacionModelAssembler;
import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.service.HospitalizacionService;
import cl.duoc.ms_hospitalizacion.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/hospitalizacion")
@Tag(name = "Hospitalizaciones", description = "Operaciones para la gestión del ingreso y alta de mascotas en la clínica")
public class HospitalizacionController {

    private static final Logger logger = LoggerFactory.getLogger(HospitalizacionController.class);

    @Autowired
    private HospitalizacionService hospitalizacionService;

    @Autowired
    private HospitalizacionModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Registrar ingreso", description = "Registra una nueva hospitalización de una mascota")
    public ResponseEntity<EntityModel<HospitalizacionDTO>> crearHospitalizacion(@Valid @RequestBody HospitalizacionDTO dto) {
        logger.info("POST /api/v1/hospitalizacion - Solicitud recibida");
        Hospitalizacion nuevo = hospitalizacionService.guardar(dto);
        logger.info("Hospitalización registrada con id={}", nuevo.getIdHospitalizacion());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listar hospitalizaciones", description = "Retorna el historial completo de hospitalizaciones")
    public ResponseEntity<CollectionModel<EntityModel<HospitalizacionDTO>>> listarHospitalizaciones() {
        logger.info("GET /api/v1/hospitalizacion - Solicitud recibida");
        
        // Lambda explícita para asegurar la compatibilidad con el Assembler
        List<EntityModel<HospitalizacionDTO>> registros = hospitalizacionService.listar().stream()
                .map(h -> assembler.toModel(h))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<HospitalizacionDTO>> collection = CollectionModel.of(registros,
                linkTo(methodOn(HospitalizacionController.class).listarHospitalizaciones()).withSelfRel());
                
        logger.info("Total de registros retornados: {}", registros.size());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener hospitalización por ID")
    public ResponseEntity<EntityModel<HospitalizacionDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/hospitalizacion/{} - Solicitud recibida", id);
        return hospitalizacionService.findById(id)
                .map(h -> ResponseEntity.ok(assembler.toModel(h)))
                .orElseThrow(() -> {
                    logger.warn("Hospitalización no encontrada id={}", id);
                    return new ResourceNotFoundException("Registro de hospitalización no encontrado con ID: " + id);
                });
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de hospitalización")
    public ResponseEntity<EntityModel<HospitalizacionDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody HospitalizacionDTO dto) {
        logger.info("PUT /api/v1/hospitalizacion/{} - Solicitud recibida", id);
        
        Hospitalizacion datosModificar = new Hospitalizacion();
        datosModificar.setIdMascota(dto.getIdMascota());
        datosModificar.setIdDueno(dto.getIdDueno());
        datosModificar.setFecha_inicio(dto.getFecha_inicio());
        datosModificar.setFecha_alta(dto.getFecha_alta());
        datosModificar.setDiagnostico(dto.getDiagnostico());

        Hospitalizacion actualizado = hospitalizacionService.actualizar(id, datosModificar);
        logger.info("Hospitalización modificada de forma exitosa id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de hospitalización")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/hospitalizacion/{} - Solicitud recibida", id);
        hospitalizacionService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(HospitalizacionController.class).listarHospitalizaciones()).withRel("historial-completo"));
        logger.info("Registro de hospitalización eliminado id={}", id);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar si la mascota está o estuvo hospitalizada")
    public ResponseEntity<Boolean> existeHospitalizacion(@PathVariable Long id) {
        logger.info("GET /api/v1/hospitalizacion/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(hospitalizacionService.existePorId(id));
    }
}
