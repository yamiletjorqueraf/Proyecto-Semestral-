package cl.duoc.ms_dueno.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import cl.duoc.ms_dueno.dto.DuenoDTO;
import cl.duoc.ms_dueno.model.Dueno;
import cl.duoc.ms_dueno.service.DuenoService;

@RestController
@RequestMapping("/api/v1/dueno")
public class DuenoController {

    private static final Logger logger = LoggerFactory.getLogger(DuenoController.class);

    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
		this.duenoService = duenoService;
	}


    @PostMapping
    public ResponseEntity<DuenoDTO> crearDueno(@RequestBody DuenoDTO duenoDto) {
        logger.info("POST /api/v1/dueno - Solicitud recibida");
        Dueno nuevo = duenoService.guardar(duenoDto.toModel());
        logger.info("Dueño creado con id={}", nuevo.getIdDueno());
        return ResponseEntity.status(HttpStatus.CREATED).body(DuenoDTO.fromModel(nuevo));
    }

    @GetMapping
    public ResponseEntity<List<DuenoDTO>> listarUsuarios() {
        logger.info("GET /api/v1/dueno - Solicitud recibida");
        List<Dueno> duenos = duenoService.listar();
        List<DuenoDTO> dtos = duenos.stream().map(DuenoDTO::fromModel).collect(Collectors.toList());
        logger.info("Total dueños retornados: {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        logger.info("GET /api/v1/dueno/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(duenoService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoDTO> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/dueno/{} - Solicitud recibida", id);
        return duenoService.findById(id)
                .map(d -> {
                    logger.info("Dueño retornado id={}", id);
                    return ResponseEntity.ok(DuenoDTO.fromModel(d));
                })
                .orElseGet(() -> {
                    logger.warn("Dueño no encontrado id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoDTO> actualizar(@PathVariable Long id, @RequestBody DuenoDTO dto) {
        logger.info("PUT /api/v1/dueno/{} - Solicitud recibida", id);
        Dueno actualizado = duenoService.actualizar(id, dto.toModel());
        logger.info("Dueño actualizado id={}", id);
        return ResponseEntity.ok(DuenoDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/dueno/{} - Solicitud recibida", id);
        duenoService.eliminar(id);
        logger.info("Dueño eliminado id={}", id);
        return ResponseEntity.noContent().build();
    }
}
