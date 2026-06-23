package cl.duoc.ms_dueno.assamblers;


import cl.duoc.ms_dueno.controller.DuenoController;
import cl.duoc.ms_dueno.dto.DuenoDTO;
import cl.duoc.ms_dueno.model.Dueno;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class DuenoModelAssembler implements RepresentationModelAssembler<Dueno, EntityModel<DuenoDTO>> {
 
    @Override
    public EntityModel<DuenoDTO> toModel(Dueno dueno) {
        DuenoDTO dto = DuenoDTO.fromModel(dueno);
        return EntityModel.of(dto,
                linkTo(methodOn(DuenoController.class).buscarPorId(dueno.getIdDueno())).withSelfRel(),
                linkTo(methodOn(DuenoController.class).listarUsuarios()).withRel("todos-los-duenos"),
                linkTo(methodOn(DuenoController.class).eliminar(dueno.getIdDueno())).withRel("eliminar")
        );
    }
}
