package cl.duoc.ms_dueno.Controller;

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

import cl.duoc.ms_dueno.Model.Dueno;
import cl.duoc.ms_dueno.Service.DuenoService;
import cl.duoc.ms_dueno.dto.DuenoDTO;

@RestController
@RequestMapping("/api/v1/dueno")
public class DuenoController {

    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
		this.duenoService = duenoService;
	}


    @PostMapping
    public ResponseEntity<DuenoDTO> crearDueno(@RequestBody DuenoDTO duenoDto) {
		Dueno nuevo = duenoService.guardar(duenoDto.toModel());
		return ResponseEntity.status(HttpStatus.CREATED).body(DuenoDTO.fromModel(nuevo));
	}

    @GetMapping
	public ResponseEntity<List<DuenoDTO>> listarUsuarios() {
		List<Dueno> usuarios = duenoService.listar();
		List<DuenoDTO> dtos = usuarios.stream().map(DuenoDTO::fromModel).collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(duenoService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoDTO> buscarPorId(@PathVariable Long id) {
        return duenoService.findById(id)
            .map(d -> ResponseEntity.ok(DuenoDTO.fromModel(d)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoDTO> actualizar(@PathVariable Long id, @RequestBody DuenoDTO dto) {
        Dueno actualizado = duenoService.actualizar(id, dto.toModel());
        return ResponseEntity.ok(DuenoDTO.fromModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        duenoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
