package cl.duoc.ms_mascota.controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_mascota.assamblers.MascotaModelAssembler;
import cl.duoc.ms_mascota.dto.MascotaDTO;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/mascota")
@Tag(name = "Mascotas V2", description = "Versión 2 - Consultas con HATEOAS")
public class MascotaControllerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(MascotaControllerV2.class);
 
    private final MascotaService mascotaService;
    private final MascotaModelAssembler assembler;
 
    public MascotaControllerV2(MascotaService mascotaService, MascotaModelAssembler assembler) {
        this.mascotaService = mascotaService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar mascotas V2")
    public CollectionModel<EntityModel<MascotaDTO>> listarMascotas() {
        logger.info("V2 GET /api/v2/mascota - Listando mascotas");
        List<EntityModel<MascotaDTO>> mascotas = mascotaService.listar().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(mascotas,
                linkTo(methodOn(MascotaControllerV2.class).listarMascotas()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID V2")
    public EntityModel<MascotaDTO> obtenerMascota(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/mascota/{} - Obteniendo mascota", id);
        Mascota mascota = mascotaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        return assembler.toModel(mascota);
    }
}
