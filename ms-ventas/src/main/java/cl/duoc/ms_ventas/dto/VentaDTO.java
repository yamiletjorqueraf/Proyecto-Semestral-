package cl.duoc.ms_ventas.dto;

import java.util.Date;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
import cl.duoc.ms_ventas.Model.Venta;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idVenta;
 
    @NotNull(message = "El ID del dueño es obligatorio")
    private Long idDueno;
 
    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long idMascota;
 
    @NotBlank(message = "El tipo de servicio es obligatorio (Consulta, Vacuna, Cirugía, Medicamento)")
    private String tipoServicio;
 
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
 
    @NotNull(message = "El monto es obligatorio")
    @Min(value = 100, message = "El monto mínimo es 100")
    @Max(value = 10000000, message = "El monto máximo es 10000000")
    private Integer monto;
 
    @NotBlank(message = "El estado es obligatorio (Completado, Pendiente, Anulado)")
    private String estado;
 
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date fecha;
 
    // ─── DTO → Model ──────────────────────────────────────────
    public Venta toModel() {
        return new Venta(
                idVenta,
                idDueno,
                idMascota,
                tipoServicio,
                descripcion,
                monto != null ? monto : 0,
                estado,
                fecha
        );
    }
 
    // ─── Model → DTO ──────────────────────────────────────────
    public static VentaDTO fromModel(Venta venta) {
        if (venta == null) return null;
        return new VentaDTO(
                venta.getIdVenta(),
                venta.getIdDueno(),
                venta.getIdMascota(),
                venta.getTipoServicio(),
                venta.getDescripcion(),
                venta.getMonto(),
                venta.getEstado(),
                venta.getFecha()
        );
    }

}
