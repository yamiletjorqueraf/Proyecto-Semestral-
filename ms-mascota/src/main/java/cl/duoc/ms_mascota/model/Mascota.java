package cl.duoc.ms_mascota.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "mascota")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMascota")
    private Long idMascota;

    @NotBlank(message = "El nombre de la mascota no debe estar en blanco")
    private String nombre;

    @NotBlank(message = "Debe especificar la especie (Perro, Gato, etc.)")
    private String especie;

    @Column(name = "raza")
    private String raza;

    @NotNull(message = "Debe registrar la edad")
    private Integer edad;

    @NotNull(message = "La mascota debe tener un dueño asignado")
    private Long idDueno; // Relación lógica por ID hacia ms-dueno
}
