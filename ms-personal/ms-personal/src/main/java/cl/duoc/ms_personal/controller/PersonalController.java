package cl.duoc.ms_personal.controller;

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

import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.service.PersonalService;

@RestController
@RequestMapping("api/v1/personal")
public class PersonalController {
private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @PostMapping
    public ResponseEntity<PersonalDTO> crearPersonal(@RequestBody PersonalDTO dto) {
        Personal nuevo = personalService.guardar(dto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(PersonalDTO.fromModel(nuevo));
    }

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> listarPersonal() {
        List<Personal> lista = personalService.listar();
        List<PersonalDTO> dtos = lista.stream().map(PersonalDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existePersonal(@PathVariable Long id) {
        return ResponseEntity.ok(personalService.existePorId(id));
    }

 @GetMapping("/{id}")
public ResponseEntity<PersonalDTO> buscarPorId(@PathVariable Long id) {
        Optional<Personal> personal = personalService.findById(id);
        if (personal.isPresent()) {
        return ResponseEntity.ok(
            PersonalDTO.fromModel(personal.get())
        );
    }

    return ResponseEntity.notFound().build();
}

    @PutMapping("/{id}")
    public ResponseEntity<PersonalDTO> actualizar(@PathVariable Long id, @RequestBody PersonalDTO dto) {
        Personal actualizado = personalService.actualizar(id, dto.toModel());
        return ResponseEntity.ok(PersonalDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        personalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
