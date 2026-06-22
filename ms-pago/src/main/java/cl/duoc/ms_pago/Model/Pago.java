package cl.duoc.ms_pago.model;

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
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @NotNull(message = "El ID de la venta es obligatorio")
    @Column(name = "idVenta", nullable = false)
    private Long idVenta;

    @NotNull(message = "El ID del dueño es obligatorio")
    @Column(name = "idDueno", nullable = false)
    private Long idDueno;

    @NotNull(message = "El valor neto es obligatorio")
    @Min(value = 100, message = "El valor mínimo es 100")
    @Max(value = 1000000, message = "El valor máximo es de 1000000")
    @Column(name = "valorNeto", nullable = false)
    private int valorNeto;
    
    @NotNull(message = "El valor del IVA es obligatorio")
    @Min(value = 0, message = "El valor mpinimo es 0")
    @Max(value = 1000000, message = "El valor máximo es de 1000000")
    @Column (name = "iva", nullable = false)
    private int iva;

    @NotNull(message = "El % de descuento es obligatorio")
    @Min(value = 0, message = "El valor mínimo es 0")
    @Max(value = 100, message = "El valor máximo es de 100")   
    @Column(name = "descuento", nullable = false)
    private int descuento;

    @NotNull(message = "El total a pagar es obligatorio")
    @Min(value = 100, message = "El valor mínimo es 100")
    @Max(value = 1000000, message = "El valor máximo es de 1000000")
    @Column(name = "total_Pagar", nullable = false)
    private int totalPagar;

    @NotBlank(message = "El medio de pago es obligatorio (Efectivo, Debito, Credito)")
    @Column(name = "medio_Pago", nullable = false, length = 50)
    private String medioPago;

    @NotBlank(message = "El estado del pago es obligatorio (Completado, Anulado, Pendiente)")
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(nullable = false)
    private Date fecha;
    

}
