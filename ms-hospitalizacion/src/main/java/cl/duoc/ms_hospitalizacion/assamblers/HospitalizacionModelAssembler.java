package cl.duoc.ms_hospitalizacion.assamblers;

import cl.duoc.ms_hospitalizacion.controller.HospitalizacionController;
import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class HospitalizacionModelAssembler implements RepresentationModelAssembler<Hospitalizacion, EntityModel<HospitalizacionDTO>> {

    @Override
    public EntityModel<HospitalizacionDTO> toModel(Hospitalizacion hospitalizacion) {
        // Usamos el método estático fromModel tal como lo definimos en tu nuevo DTO
        HospitalizacionDTO dto = HospitalizacionDTO.fromModel(hospitalizacion);

        // Retornamos el EntityModel inyectando los enlaces REST hipermedia correspondientes
        return EntityModel.of(dto,
                linkTo(methodOn(HospitalizacionController.class).buscarPorId(hospitalizacion.getIdHospitalizacion())).withSelfRel(),
                linkTo(methodOn(HospitalizacionController.class).listarHospitalizaciones()).withRel("todas-las-hospitalizaciones"),
                linkTo(methodOn(HospitalizacionController.class).eliminar(hospitalizacion.getIdHospitalizacion())).withRel("eliminar")
        );
    }
}