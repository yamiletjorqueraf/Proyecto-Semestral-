package com.veterinaria.personal_service.dto;

import com.veterinaria.personal_service.model.Personal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalDTO {
    private Long id_personal;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String correo;

    public Personal toModel() {
        return new Personal(id_personal, nombre,apellido, especialidad,telefono, correo);
    }

    public static PersonalDTO fromModel(Personal p) {
        if (p == null) return null;
        return new PersonalDTO(p.getId_personal(), p.getNombre(),p.getApellido(),p.getEspecialidad(),p.getTelefono(), p.getCorreo());
    }

}
