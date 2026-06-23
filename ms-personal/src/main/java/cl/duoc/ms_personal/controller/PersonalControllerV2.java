package cl.duoc.ms_personal.controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
 
import cl.duoc.ms_personal.assamblers.PersonalModelAssembler;
import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.service.PersonalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@RestController
@RequestMapping("/api/v2/personal")
@Tag(name = "Personal V2", description = "Versión 2 - Consultas con HATEOAS")
public class PersonalControllerV2 {
 
    private static final Logger logger = LoggerFactory.getLogger(PersonalControllerV2.class);
 
    private final PersonalService personalService;
    private final PersonalModelAssembler assembler;
 
    public PersonalControllerV2(PersonalService personalService, PersonalModelAssembler assembler) {
        this.personalService = personalService;
        this.assembler = assembler;
    }
 
    @GetMapping
    @Operation(summary = "Listar personal V2")
    public CollectionModel<EntityModel<PersonalDTO>> listarPersonal() {
        logger.info("V2 GET /api/v2/personal - Listando personal");
        List<EntityModel<PersonalDTO>> lista = personalService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(lista,
                linkTo(methodOn(PersonalControllerV2.class).listarPersonal()).withSelfRel());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Obtener personal por ID V2")
    public EntityModel<PersonalDTO> obtenerPersonal(@PathVariable Long id) {
        logger.info("V2 GET /api/v2/personal/{} - Obteniendo personal", id);
        Personal personal = personalService.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));
        return assembler.toModel(personal);
    }
}
