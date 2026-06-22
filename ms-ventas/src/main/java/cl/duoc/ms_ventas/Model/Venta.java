package cl.duoc.ms_ventas.Model;

import java.util.Date;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Venta {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;
 
    @NotNull(message = "El ID del dueño es obligatorio")
    @Column(name = "idDueno", nullable = false)
    private Long idDueno;
 
    @NotNull(message = "El ID de la mascota es obligatorio")
    @Column(name = "idMascota", nullable = false)
    private Long idMascota;
 
    @NotBlank(message = "El tipo de servicio es obligatorio (Consulta, Vacuna, Cirugía, Medicamento)")
    @Column(name = "tipoServicio", nullable = false, length = 50)
    private String tipoServicio;
 
    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
 
    @NotNull(message = "El monto es obligatorio")
    @Min(value = 100, message = "El monto mínimo es 100")
    @Max(value = 10000000, message = "El monto máximo es 10000000")
    @Column(name = "monto", nullable = false)
    private int monto;
 
    @NotBlank(message = "El estado es obligatorio (Completado, Pendiente, Anulado)")
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;
 
    @Column(nullable = false)
    private Date fecha;


}
