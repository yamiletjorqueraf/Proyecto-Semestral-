package cl.duoc.ms_resultados_examenes.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoExamenDTO {
    private Long idResultado;
    private Long idMascota;
    private String tipoExamen;
    private LocalDate fechaExamen;
    private String resultado;
    private Long idPersonal;
    private String personalCargo;

    public static ResultadoExamenDTO fromModel(ResultadoExamen examen) {
        return new ResultadoExamenDTO(
            examen.getIdResultado(),
            examen.getIdMascota(),
            examen.getTipoExamen(),
            examen.getFechaExamen(),
            examen.getResultado(),
            examen.getIdPersonal(),
            examen.getPersonalCargo()
        );
    }

    public ResultadoExamen toModel() {
        return new ResultadoExamen(
            idResultado, 
            idMascota, 
            tipoExamen, 
            fechaExamen, 
            resultado, 
            idPersonal,
            personalCargo
        );
    }
}