package com.veterinaria.personal_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.personal_service.dto.PersonalDTO;
import com.veterinaria.personal_service.model.Personal;
import com.veterinaria.personal_service.service.PersonalService;

@RestController
@RequestMapping("/personal")
public class PersonalController {
      private final PersonalService personalService;

      public PersonalController(PersonalService personalService){
            this.personalService = personalService;
      }
      @PostMapping
    public ResponseEntity<PersonalDTO> crearPersonal(@RequestBody PersonalDTO personalDto) {
        Personal nuevoPersonal = personalService.guardar(personalDto.toModel());
        return ResponseEntity.ok(PersonalDTO.fromModel(nuevoPersonal));
    }

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> listarPesonal() {
        List<Personal> personal = personalService.listar();
        List<PersonalDTO> dtos = personal.stream().map(PersonalDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCliente(@PathVariable Long id) {
        return ResponseEntity.ok(personalService.existePorId(id));
    }

}
