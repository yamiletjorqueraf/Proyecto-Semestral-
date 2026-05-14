package com.Veterinaria.mascota_service.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Veterinaria.mascota_service.DTO.MascotaDTO;
import com.Veterinaria.mascota_service.Model.Mascota;
import com.Veterinaria.mascota_service.Service.MascotaService;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {
private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@RequestBody MascotaDTO mascotaDto) {
        Mascota nuevaMascota = mascotaService.guardar(mascotaDto.toModel());
        return ResponseEntity.ok(MascotaDTO.fromModel(nuevaMascota));
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listarMascotas() {
        List<Mascota> mascotas = mascotaService.listar();
        List<MascotaDTO> dtos = mascotas.stream().map(MascotaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id_mascota}/exists")
    public ResponseEntity<Boolean> existeMascota(@PathVariable Long id_mascota) {
        return ResponseEntity.ok(mascotaService.existePorId(id_mascota));
    }

}
