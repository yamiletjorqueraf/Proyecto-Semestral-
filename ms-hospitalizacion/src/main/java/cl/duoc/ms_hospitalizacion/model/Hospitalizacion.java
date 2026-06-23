package cl.duoc.ms_hospitalizacion.model;

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
@Table(name = "hospitalizacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hospitalizacion { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHospitalizacion")
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