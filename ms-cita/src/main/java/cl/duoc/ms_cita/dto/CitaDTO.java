package cl.duoc.ms_cita.dto;

import java.time.LocalDateTime;

import cl.duoc.ms_cita.Model.Cita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {

    private Long idCita;
    private Long idMascota;
    private Long idPersonal;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;

    public Cita toModel() {
        return new Cita(idCita, idMascota, idPersonal, fechaHora, motivo, estado);
    }


    public static CitaDTO fromModel(Cita c) {
        if (c == null) return null;
        return new CitaDTO(
            c.getIdCita(),c.getIdMascota(), c.getIdPersonal(), c.getFechaHora(), c.getMotivo(), c.getEstado()
        );
    }

}
