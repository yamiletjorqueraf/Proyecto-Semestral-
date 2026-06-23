package cl.duoc.ms_farmacia.dto;

import cl.duoc.ms_farmacia.model.Medicamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDTO {
    private Long idMedicamento;
    private String nombre;
    private String descripcion; // Atributo mapeado de tu modelo
    private Double precio;
    private Integer stock;

    public static MedicamentoDTO fromModel(Medicamento medicamento) {
        return new MedicamentoDTO(
            medicamento.getIdMedicamento(),
            medicamento.getNombre(),
            medicamento.getDescripcion(),
            medicamento.getPrecio(),
            medicamento.getStock()
        );
    }

    public Medicamento toModel() {
        return new Medicamento(idMedicamento, nombre, descripcion, precio, stock);
    }
}