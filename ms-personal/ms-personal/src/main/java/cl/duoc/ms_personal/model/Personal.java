package cl.duoc.ms_personal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonal")
    private Long idPersonal;

    @NotBlank(message = "El nombre no debe estar en blanco")
    private String nombre;

    @NotBlank(message = "El apellido no debe estar en blanco")
    private String apellido;

    @NotBlank(message = "Debe asignar un cargo (Veterinario, Técnico, Recepcionista)")
    private String cargo;

    @Email
    @NotBlank(message = "Debe ingresar un correo institucional")
    private String correo;

    private boolean activo = true;
}
