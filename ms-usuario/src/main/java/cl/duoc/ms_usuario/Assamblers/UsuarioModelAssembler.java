package cl.duoc.ms_usuario.Assamblers;
import cl.duoc.ms_usuario.Controller.UsuarioController;
import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.dto.UsuarioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioDTO>> {
 
    @Override
    public EntityModel<UsuarioDTO> toModel(Usuario usuario) {
        UsuarioDTO dto = UsuarioDTO.fromModel(usuario);
        return EntityModel.of(dto,
                linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("todos-los-usuarios"),
                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getIdUsuario())).withRel("eliminar")
        );
    }
}
