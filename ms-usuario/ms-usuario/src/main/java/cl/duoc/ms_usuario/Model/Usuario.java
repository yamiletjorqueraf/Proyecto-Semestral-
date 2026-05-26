package cl.duoc.ms_usuario.Model;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @NotBlank(message = "El nombre del usuario no debe estar en blanco")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido del usuario no debe estar en blanco")
    @Column(name = "apellido")
    private String apellido;


    @NotBlank(message = "Debe existir un rol(Admin-Personal-Cliente)")
    @Column(name = "rol")
    private String rol;

    @Email
    @NotBlank(message = "Debe ingresar un correo")
    @Column(name = "correo")
    private String correo;


    @NotNull(message = "Debe tener numero de contacto")
    @Column(name = "telefono")
    private Long telefono;
     private String username;
    private String password;

  

}
