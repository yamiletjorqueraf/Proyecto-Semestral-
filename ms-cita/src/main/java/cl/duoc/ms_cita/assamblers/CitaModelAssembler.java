package cl.duoc.ms_cita.assamblers;

import cl.duoc.ms_cita.controller.CitaController;
import cl.duoc.ms_cita.model.Cita;
import cl.duoc.ms_cita.dto.CitaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class CitaModelAssembler implements RepresentationModelAssembler<Cita, EntityModel<CitaDTO>> {
 
    @Override
    public EntityModel<CitaDTO> toModel(Cita cita) {
        CitaDTO dto = CitaDTO.fromModel(cita);
        return EntityModel.of(dto,
                linkTo(methodOn(CitaController.class).buscarPorId(cita.getIdCita())).withSelfRel(),
                linkTo(methodOn(CitaController.class).listarCitas()).withRel("todas-las-citas"),
                linkTo(methodOn(CitaController.class).eliminar(cita.getIdCita())).withRel("eliminar")
        );
    }
}
