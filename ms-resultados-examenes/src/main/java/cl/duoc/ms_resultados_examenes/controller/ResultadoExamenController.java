package cl.duoc.ms_resultados_examenes.controller;

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

import cl.duoc.ms_resultados_examenes.assamblers.ResultadoExamenModelAssembler;
import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import cl.duoc.ms_resultados_examenes.service.ResultadoExamenService;
import cl.duoc.ms_resultados_examenes.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/resultado-examen")
@Tag(name = "Resultados Examen", description = "Operaciones para gestionar los informes clínicos y exámenes de las mascotas")
public class ResultadoExamenController {

    private static final Logger logger = LoggerFactory.getLogger(ResultadoExamenController.class);

    @Autowired
    private ResultadoExamenService resultadoExamenService;

    @Autowired
    private ResultadoExamenModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Registrar resultado de examen")
    public ResponseEntity<EntityModel<ResultadoExamenDTO>> crearResultado(@Valid @RequestBody ResultadoExamenDTO dto) {
        logger.info("POST /api/v1/resultado-examen - Solicitud recibida");
        ResultadoExamen nuevo = resultadoExamenService.guardar(dto);
        logger.info("Resultado de examen registrado con id={}", nuevo.getIdResultado());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listar todos los resultados")
    public ResponseEntity<CollectionModel<EntityModel<ResultadoExamenDTO>>> listarResultados() {
        logger.info("GET /api/v1/resultado-examen - Solicitud recibida");
        
        // Mapeo explícito mediante Lambda para evitar problemas con referencias de métodos en el IDE
        List<EntityModel<ResultadoExamenDTO>> resultados = resultadoExamenService.listar().stream()
                .map(re -> assembler.toModel(re))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ResultadoExamenDTO>> collection = CollectionModel.of(resultados,
                linkTo(methodOn(ResultadoExamenController.class).listarResultados()).withSelfRel());
                
        logger.info("Total de resultados devueltos: {}", resultados.size());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener resultado por ID")
    public ResponseEntity<EntityModel<ResultadoExamenDTO>> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/resultado-examen/{} - Solicitud recibida", id);
        return resultadoExamenService.findById(id)
                .map(re -> ResponseEntity.ok(assembler.toModel(re)))
                .orElseThrow(() -> {
                    logger.warn("Resultado de examen no encontrado con id={}", id);
                    return new ResourceNotFoundException("Resultado no encontrado con ID: " + id);
                });
    }

    @GetMapping("/mascota/{idMascota}")
    @Operation(summary = "Listar resultados de una mascota")
    public ResponseEntity<CollectionModel<EntityModel<ResultadoExamenDTO>>> buscarPorMascota(@PathVariable Long idMascota) {
        logger.info("GET /api/v1/resultado-examen/mascota/{} - Solicitud recibida", idMascota);
        
        List<EntityModel<ResultadoExamenDTO>> resultados = resultadoExamenService.buscarPorMascota(idMascota).stream()
                .map(re -> assembler.toModel(re))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ResultadoExamenDTO>> collection = CollectionModel.of(resultados,
                linkTo(methodOn(ResultadoExamenController.class).buscarPorMascota(idMascota)).withSelfRel());
                
        return ResponseEntity.ok(collection);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un resultado de examen")
    public ResponseEntity<EntityModel<ResultadoExamenDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody ResultadoExamenDTO dto) {
        logger.info("PUT /api/v1/resultado-examen/{} - Solicitud recibida", id);
        
        ResultadoExamen datosModificar = new ResultadoExamen();
        datosModificar.setIdMascota(dto.getIdMascota());
        datosModificar.setTipoExamen(dto.getTipoExamen());
        datosModificar.setFechaExamen(dto.getFechaExamen());
        datosModificar.setResultado(dto.getResultado());
        datosModificar.setIdPersonal(dto.getIdPersonal());
        datosModificar.setPersonalCargo(dto.getPersonalCargo());

        ResultadoExamen actualizado = resultadoExamenService.actualizar(id, datosModificar);
        logger.info("Resultado de examen modificado id={}", id);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un resultado")
    public ResponseEntity<EntityModel<Void>> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/resultado-examen/{} - Solicitud recibida", id);
        resultadoExamenService.eliminar(id);
        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(ResultadoExamenController.class).listarResultados()).withRel("todos-los-resultados"));
        logger.info("Resultado de examen eliminado id={}", id);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar existencia de un informe")
    public ResponseEntity<Boolean> existeResultado(@PathVariable Long id) {
        logger.info("GET /api/v1/resultado-examen/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(resultadoExamenService.existePorId(id));
    }
}