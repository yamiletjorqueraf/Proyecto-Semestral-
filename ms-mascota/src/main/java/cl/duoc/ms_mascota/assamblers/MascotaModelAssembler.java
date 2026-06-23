package cl.duoc.ms_mascota.assamblers;

import cl.duoc.ms_mascota.controller.MascotaController;
import cl.duoc.ms_mascota.dto.MascotaDTO;
import cl.duoc.ms_mascota.model.Mascota;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class MascotaModelAssembler implements RepresentationModelAssembler<Mascota, EntityModel<MascotaDTO>> {
 
    @Override
    public EntityModel<MascotaDTO> toModel(Mascota mascota) {
        MascotaDTO dto = MascotaDTO.fromModel(mascota);
        return EntityModel.of(dto,
                linkTo(methodOn(MascotaController.class).buscarPorId(mascota.getIdMascota())).withSelfRel(),
                linkTo(methodOn(MascotaController.class).listarMascotas()).withRel("todas-las-mascotas"),
                linkTo(methodOn(MascotaController.class).eliminar(mascota.getIdMascota())).withRel("eliminar")
        );
    }
}
