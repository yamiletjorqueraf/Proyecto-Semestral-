package cl.duoc.ms_usuario.Controller;

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

import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Service.UsuarioService;
import cl.duoc.ms_usuario.dto.UsuarioDTO;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDto) {
		Usuario nuevo = usuarioService.guardar(usuarioDto.toModel());
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.fromModel(nuevo));
	}

    @GetMapping
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.listar();
		List<UsuarioDTO> dtos = usuarios.stream().map(UsuarioDTO::fromModel).collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.findById(id)
            .map(u -> ResponseEntity.ok(UsuarioDTO.fromModel(u)))
            .orElse(ResponseEntity.notFound().build());
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        Usuario actualizado = usuarioService.actualizar(id, dto.toModel());
        return ResponseEntity.ok(UsuarioDTO.fromModel(actualizado));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
