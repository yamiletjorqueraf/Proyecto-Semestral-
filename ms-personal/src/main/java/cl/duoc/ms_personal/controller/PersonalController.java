package cl.duoc.ms_personal.controller;

import java.util.List;
import java.util.Optional;
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

import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.service.PersonalService;

@RestController
@RequestMapping("/api/v1/personal")
public class PersonalController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @PostMapping
    public ResponseEntity<PersonalDTO> crearPersonal(@RequestBody PersonalDTO dto) {
        logger.info("POST /api/v1/personal - Solicitud recibida");
        Personal nuevo = personalService.guardar(dto.toModel());
        logger.info("Personal creado con id={}", nuevo.getIdPersonal());
        return ResponseEntity.status(HttpStatus.CREATED).body(PersonalDTO.fromModel(nuevo));
    }

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> listarPersonal() {
        logger.info("GET /api/v1/personal - Solicitud recibida");
        List<Personal> lista = personalService.listar();
        List<PersonalDTO> dtos = lista.stream().map(PersonalDTO::fromModel).collect(Collectors.toList());
        logger.info("Total personal retornado: {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existePersonal(@PathVariable Long id) {
        logger.info("GET /api/v1/personal/{}/exists - Solicitud recibida", id);
        return ResponseEntity.ok(personalService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDTO> buscarPorId(@PathVariable Long id) {
        logger.info("GET /api/v1/personal/{} - Solicitud recibida", id);
        Optional<Personal> personal = personalService.findById(id);
        if (personal.isPresent()) {
            logger.info("Personal retornado id={}", id);
            return ResponseEntity.ok(PersonalDTO.fromModel(personal.get()));
        }
        logger.warn("Personal no encontrado id={}", id);
        return ResponseEntity.notFound().build();
    }

    

    @PutMapping("/{id}")
    public ResponseEntity<PersonalDTO> actualizar(@PathVariable Long id, @RequestBody PersonalDTO dto) {
        logger.info("PUT /api/v1/personal/{} - Solicitud recibida", id);
        Personal actualizado = personalService.actualizar(id, dto.toModel());
        logger.info("Personal actualizado id={}", id);
        return ResponseEntity.ok(PersonalDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/v1/personal/{} - Solicitud recibida", id);
        personalService.eliminar(id);
        logger.info("Personal eliminado id={}", id);
        return ResponseEntity.noContent().build();
    }
}
