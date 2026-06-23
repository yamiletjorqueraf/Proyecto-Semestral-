package cl.duoc.ms_hospitalizacion.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HospitalizacionDTO {

    private Long idHospitalizacion;

    @NotNull(message = "El id de la mascota no debe estar en blanco")
    private Long idMascota;
    
    @NotNull(message = "El id del dueño no debe estar en blanco")
    private Long idDueno;

    @NotNull(message = "Debe ingresar la fecha de inicio de la hospitalización")
    private LocalDate fecha_inicio;
    
    @NotNull(message = "Debe ingresar la fecha de termino de la hospitalización")
    private LocalDate fecha_alta;

    @NotBlank(message = "Debe ingresar un diagnostico")
    private String diagnostico;
}