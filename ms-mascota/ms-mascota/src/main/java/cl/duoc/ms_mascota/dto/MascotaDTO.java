package cl.duoc.ms_mascota.dto;

import cl.duoc.ms_mascota.model.Mascota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaDTO {
    private Long idMascota;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private Long idDueno;

    public static MascotaDTO fromModel(Mascota mascota) {
        return new MascotaDTO(
            mascota.getIdMascota(),
            mascota.getNombre(),
            mascota.getEspecie(),
            mascota.getRaza(),
            mascota.getEdad(),
            mascota.getIdDueno()
        );
    }

    public Mascota toModel() {
        return new Mascota(idMascota, nombre, especie, raza, edad, idDueno);
    }

}
