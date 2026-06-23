package cl.duoc.ms_dueno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDueno")
    private Long idDueno;

    @NotNull(message = "Debe asociar un usuario")
    @Column(name = "idUsuario")
    private Long idUsuario;

    //@NotNull(message = "Debe asociar una mascota")
    //@Column(name = "idMascota")
    //private Long idMascota;
    
    @NotBlank(message = "El nombre del usuario no debe estar en blanco")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido del usuario no debe estar en blanco")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "El rut no debe estar en blanco")
    @Column(name = "rut")
    private String rut;

    @Email
    @NotBlank(message = "Debe ingresar un correo")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Debe tener numero de contacto")
    @Column(name = "telefono")
    private Long telefono;

    @NotBlank(message = "Debe tener una direccion")
    @Column(name = "direccion")
    private String direccion;


}
