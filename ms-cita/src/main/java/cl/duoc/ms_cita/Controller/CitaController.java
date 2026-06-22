package cl.duoc.ms_cita.Controller;

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

import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Service.CitaService;
import cl.duoc.ms_cita.dto.CitaDTO;

@RestController
@RequestMapping("/api/v1/cita")
public class CitaController {

    private static final Logger logger = LoggerFactory.getLogger(CitaController.class);

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crearCita(@RequestBody CitaDTO citaDto) {
        logger.info("POST /api/v1/cita - Solicitud recibida");
        Cita nueva = citaService.guardar(citaDto.toModel());
        logger.info("Cita creada con id={}", nueva.getIdCita());
        return ResponseEntity.status(HttpStatus.CREATED).body(CitaDTO.fromModel(nueva));
    }

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas() {
        logger.info("GET /api/v1/cita - Solicitud recibida");
        List<Cita> citas = citaService.listar();
        List<CitaDTO> dtos = citas.stream().map(CitaDTO::fromModel).collect(Collectors.toList());
        logger.info("Total citas retornadas: {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCita(@PathVariable Long id) {
        logger.info("GET /api/v1/cita/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(citaService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/cita/{} - Solicitud recibida", id);
        return citaService.findById(id)
                .map(c -> {
                    logger.info("Cita retornada id={}", id);
                    return ResponseEntity.ok(CitaDTO.fromModel(c));
                })
                .orElseGet(() -> {
                    logger.warn("Cita no encontrada id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizar(@PathVariable Long id, @RequestBody CitaDTO dto) {
        logger.info("PUT /api/v1/cita/{} - Solicitud recibida", id);
        Cita actualizada = citaService.actualizar(id, dto.toModel());
        logger.info("Cita actualizada id={}", id);
        return ResponseEntity.ok(CitaDTO.fromModel(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/cita/{} - Solicitud recibida", id);
        citaService.eliminar(id);
        logger.info("Cita eliminada id={}", id);
        return ResponseEntity.noContent().build();
    }
    

}
