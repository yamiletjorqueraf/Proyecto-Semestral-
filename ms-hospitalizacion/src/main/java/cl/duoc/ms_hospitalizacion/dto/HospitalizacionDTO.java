package cl.duoc.ms_hospitalizacion.dto;

import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalizacionDTO {
    private Long idHospitalizacion;
    private Long idMascota;
    private Long idDueno;
    private LocalDate fecha_inicio;
    private LocalDate fecha_alta;
    private String diagnostico; // Ejemplo: "En observación", "Alta"

    public static HospitalizacionDTO fromModel(Hospitalizacion hospitalizacion) {
        return new HospitalizacionDTO(
            hospitalizacion.getIdHospitalizacion(),
            hospitalizacion.getIdMascota(),
            hospitalizacion.getIdDueno(),
            hospitalizacion.getFecha_inicio(),
            hospitalizacion.getFecha_alta(),
            hospitalizacion.getDiagnostico());
    }

    public Hospitalizacion toModel() {
        return new Hospitalizacion(
            idHospitalizacion,
            idMascota,
            idDueno,
            fecha_inicio,
            fecha_alta,
            diagnostico
        );
    }
}