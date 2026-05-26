package cl.duoc.ms_mascota.controller;

import java.util.List;
import java.util.Optional;
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

import cl.duoc.ms_mascota.dto.MascotaDTO;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.service.MascotaService;

@RestController
@RequestMapping("api/v1/mascota")
public class MascotaController {
 private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@RequestBody MascotaDTO dto) {
        Mascota nueva = mascotaService.guardar(dto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(MascotaDTO.fromModel(nueva));
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listarMascotas() {
        List<Mascota> mascotas = mascotaService.listar();
        List<MascotaDTO> dtos = mascotas.stream().map(MascotaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeMascota(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.findById(id);
        if (mascota.isPresent()) {
        return ResponseEntity.ok(
            MascotaDTO.fromModel(mascota.get())
        );
    }

    return ResponseEntity.notFound().build();
}

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> actualizar(@PathVariable Long id, @RequestBody MascotaDTO dto) {
        Mascota actualizada = mascotaService.actualizar(id, dto.toModel());
        return ResponseEntity.ok(MascotaDTO.fromModel(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
