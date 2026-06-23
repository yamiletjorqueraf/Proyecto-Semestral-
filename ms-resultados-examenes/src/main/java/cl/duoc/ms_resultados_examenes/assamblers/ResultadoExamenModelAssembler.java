package cl.duoc.ms_resultados_examenes.assamblers;

import cl.duoc.ms_resultados_examenes.controller.ResultadoExamenController;
import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ResultadoExamenModelAssembler implements RepresentationModelAssembler<ResultadoExamen, EntityModel<ResultadoExamenDTO>> {

    @Override
    public EntityModel<ResultadoExamenDTO> toModel(ResultadoExamen entity) {
        ResultadoExamenDTO dto = entity.toDto();

        return EntityModel.of(dto,
                linkTo(methodOn(ResultadoExamenController.class).buscarPorId(entity.getIdResultado())).withSelfRel(),
                linkTo(methodOn(ResultadoExamenController.class).listarResultados()).withRel("todos-los-resultados"),
                linkTo(methodOn(ResultadoExamenController.class).eliminar(entity.getIdResultado())).withRel("eliminar")
        );
    }
}