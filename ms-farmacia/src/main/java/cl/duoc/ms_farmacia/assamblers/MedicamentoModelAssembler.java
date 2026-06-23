package cl.duoc.ms_farmacia.assamblers;

import cl.duoc.ms_farmacia.controller.MedicamentoController;
import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MedicamentoModelAssembler implements RepresentationModelAssembler<Medicamento, EntityModel<MedicamentoDTO>> {

  @Override
public EntityModel<MedicamentoDTO> toModel(Medicamento medicamento) {
    // En lugar de llamar a .toDto(), pasamos los datos campo por campo:
    MedicamentoDTO dto = new MedicamentoDTO();
    dto.setIdMedicamento(medicamento.getIdMedicamento());
    dto.setNombre(medicamento.getNombre());
    dto.setDescripcion(medicamento.getDescripcion()); 
    dto.setPrecio(medicamento.getPrecio());
    dto.setStock(medicamento.getStock());

    return EntityModel.of(dto,
            linkTo(methodOn(MedicamentoController.class).buscarPorId(medicamento.getIdMedicamento())).withSelfRel(),
            linkTo(methodOn(MedicamentoController.class).listarMedicamentos()).withRel("todos-los-medicamentos"),
            linkTo(methodOn(MedicamentoController.class).eliminar(medicamento.getIdMedicamento())).withRel("eliminar")
    );
}
}