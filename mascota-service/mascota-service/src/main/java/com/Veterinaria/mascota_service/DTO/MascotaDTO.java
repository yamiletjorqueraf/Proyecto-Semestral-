package com.Veterinaria.mascota_service.DTO;

import java.util.Date;

import com.Veterinaria.mascota_service.Model.Mascota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MascotaDTO {
  private Long id_mascota;
    private String nombre;
    private String raza;
    private String tipo_animal;
    private Date fecha_nacimiento;
    private Long id_dueño;
    private String detalle;
    
    public Mascota toModel() {
        return new Mascota(id_mascota, nombre, raza,tipo_animal,fecha_nacimiento,id_dueño,detalle);
    }
       public static MascotaDTO fromModel(Mascota m) {
        if (m == null) return null;
        return new MascotaDTO(m.getId_mascota(), m.getNombre(), m.getRaza(),m.getTipo_animal(),m.getFecha_nacimiento(),m.getId_dueño(),m.getDetalle());
    }
}
