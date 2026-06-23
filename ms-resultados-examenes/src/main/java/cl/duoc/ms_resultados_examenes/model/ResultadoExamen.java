package cl.duoc.ms_resultados_examenes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resultados_examen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoExamen {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResultado")
    private Long idResultado;

    @NotNull(message = "El ID de la mascota no puede ser nulo")
    private Long idMascota;

    @NotBlank(message = "El tipo de examen no puede estar vacío")
    private String tipoExamen;

    @NotNull(message = "La fecha del examen es obligatoria")
    private LocalDate fechaExamen;

    @NotBlank(message = "Debe ingresar el resultado o diagnóstico del examen")
    @Column(length = 1000) // Por si el diagnóstico es largo
    private String resultado;

    @NotNull(message = "El ID del personal no puede ser nulo")
    private Long idPersonal;

    @NotBlank(message = "El nombre del veterinario firmante es obligatorio")
    private String personalCargo;
     
}
