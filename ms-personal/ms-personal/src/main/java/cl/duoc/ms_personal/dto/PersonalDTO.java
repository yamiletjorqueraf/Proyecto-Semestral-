package cl.duoc.ms_personal.dto;

import cl.duoc.ms_personal.model.Personal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO {
    private Long idPersonal;
    private String nombre;
    private String apellido;
    private String cargo;
    private String correo;
    private boolean activo;

    public static PersonalDTO fromModel(Personal personal) {
        return new PersonalDTO(
            personal.getIdPersonal(),
            personal.getNombre(),
            personal.getApellido(),
            personal.getCargo(),
            personal.getCorreo(),
            personal.isActivo()
        );
    }

    public Personal toModel() {
        return new Personal(idPersonal, nombre, apellido, cargo, correo, activo);
    }
}
