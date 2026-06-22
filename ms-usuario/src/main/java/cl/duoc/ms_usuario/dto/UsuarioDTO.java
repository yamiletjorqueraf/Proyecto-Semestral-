package cl.duoc.ms_usuario.dto;

import cl.duoc.ms_usuario.Model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String rol;
    private String correo;
    private Long telefono;


    public Usuario toModel() {
        return new Usuario(idUsuario, nombre, apellido, rol, correo, telefono);
    }

    public static UsuarioDTO fromModel(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getRol(), u.getCorreo(), u.getTelefono());
    }


}
