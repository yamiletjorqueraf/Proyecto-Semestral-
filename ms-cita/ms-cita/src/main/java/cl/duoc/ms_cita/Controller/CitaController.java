package cl.duoc.ms_cita.Controller;

import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("api/v1/cita")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crearCita(@RequestBody CitaDTO citaDto) {
        Cita nueva = citaService.guardar(citaDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(CitaDTO.fromModel(nueva));
    }

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas() {
        List<Cita> citas = citaService.listar();
        List<CitaDTO> dtos = citas.stream().map(CitaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> buscarPorId(@PathVariable Long id) {
        return citaService.findById(id)
                .map(c -> ResponseEntity.ok(CitaDTO.fromModel(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizar(@PathVariable Long id, @RequestBody CitaDTO dto) {
        Cita actualizada = citaService.actualizar(id, dto.toModel());
        return ResponseEntity.ok(CitaDTO.fromModel(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    

}
