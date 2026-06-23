package cl.duoc.ms_farmacia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDTO {
 private Long idMedicamento;

    @NotBlank(message = "El nombre del medicamento no debe estar en blanco")
    private String nombre;

    @NotBlank(message = "Debe ingresar la descripción o compuesto activo")
    private String description; // Mapeado con la propiedad correspondiente

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio debe ser un valor positivo")
    private Double precio;

    @NotNull(message = "Debe registrar la cantidad en stock disponible")
    @Min(value = 0, message = "El stock no puede ser un número negativo")
    private Integer stock;
}
