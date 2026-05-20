package cl.duoc.ms_cita.Model;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private Long idCita;

    @NotNull(message = "Debe asociar una mascota")
    @Column(name = "idMascota")
    private Long idMascota;

    @NotNull(message = "Debe asociar un personal")
    @Column(name = "idPersonal")
    private Long idPersonal;

    @NotNull(message = "La fecha y hora no puede ser nula")
    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo no puede estar en blanco")
    @Column(name = "motivo")
    private String motivo;

    @NotBlank(message = "El estado no puede estar en blanco (En curso o finalizado")
    @Column(name = "estado")
    private String estado;

}
