package cl.duoc.ms_personal.assamblers;
import cl.duoc.ms_personal.controller.PersonalController;
import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class PersonalModelAssembler implements RepresentationModelAssembler<Personal, EntityModel<PersonalDTO>> {
 
    @Override
    public EntityModel<PersonalDTO> toModel(Personal personal) {
        PersonalDTO dto = PersonalDTO.fromModel(personal);
        return EntityModel.of(dto,
                linkTo(methodOn(PersonalController.class).buscarPorId(personal.getIdPersonal())).withSelfRel(),
                linkTo(methodOn(PersonalController.class).listarPersonal()).withRel("todo-el-personal"),
                linkTo(methodOn(PersonalController.class).eliminar(personal.getIdPersonal())).withRel("eliminar")
        );
    }
}
