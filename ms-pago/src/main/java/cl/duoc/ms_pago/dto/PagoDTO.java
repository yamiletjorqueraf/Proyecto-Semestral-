package cl.duoc.ms_pago.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import cl.duoc.ms_pago.model.Pago;
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
public class PagoDTO {

    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idPago;

    @NotNull(message = "El ID de la venta es obligatorio")
    private Long idVenta;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long idDueno;

    @NotNull(message = "El valor neto es obligatorio")
    @Min(value = 100, message = "El valor mínimo es 100")
    @Max(value = 1000000, message = "El valor máximo es 1000000")
    private Integer valorNeto;

    // Calculado por el service, no se recibe del cliente
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer iva;

    @NotNull(message = "El % de descuento es obligatorio")
    @Min(value = 0, message = "El valor mínimo es 0")
    @Max(value = 100, message = "El valor máximo es 100")
    private Integer descuento;

    // Calculado por el service, no se recibe del cliente
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalPagar;

    @NotBlank(message = "El medio de pago es obligatorio (Efectivo, Debito, Credito)")
    private String medioPago;

    @NotBlank(message = "El estado del pago es obligatorio (Completado, Anulado, Pendiente)")
    private String estado;

    // Asignada por el service, no se recibe del cliente
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date fecha;

    
    public Pago toModel() {
        return new Pago(
                idPago,
                idVenta,
                idDueno,
                valorNeto != null ? valorNeto : 0,
                iva != null ? iva : 0,
                descuento != null ? descuento : 0,
                totalPagar != null ? totalPagar : 0,
                medioPago,
                estado,
                fecha
        );
    }

    
    public static PagoDTO fromModel(Pago pago) {
        if (pago == null) return null;
        return new PagoDTO(
                pago.getIdPago(),
                pago.getIdVenta(),
                pago.getIdDueno(),
                pago.getValorNeto(),
                pago.getIva(),
                pago.getDescuento(),
                pago.getTotalPagar(),
                pago.getMedioPago(),
                pago.getEstado(),
                pago.getFecha()
        );
    }
}
