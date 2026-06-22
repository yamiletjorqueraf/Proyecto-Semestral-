package cl.duoc.ms_dueno.dto;

import cl.duoc.ms_dueno.model.Dueno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DuenoDTO {
    private Long idDueno;
    private Long idUsuario;
    //private Long idMascota;
    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private Long telefono;
    private String direccion;

    public Dueno toModel() {
        return new Dueno(idDueno, idUsuario, nombre, apellido, rut, email, telefono, direccion);
    }

    public static DuenoDTO fromModel(Dueno d) {
        if (d == null) return null;
        return new DuenoDTO(d.getIdDueno(), d.getIdUsuario(), d.getNombre(), d.getApellido(), d.getRut(), d.getEmail(), d.getTelefono(), d.getDireccion());
    }


}
