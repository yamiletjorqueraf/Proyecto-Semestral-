package cl.duoc.ms_pago.assamblers;


import cl.duoc.ms_pago.Controller.PagoController;
import cl.duoc.ms_pago.dto.PagoDTO;
import cl.duoc.ms_pago.Model.Pago;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<PagoDTO>> {
    @Override
    public EntityModel<PagoDTO> toModel(Pago pago) {
        PagoDTO dto = PagoDTO.fromModel(pago);
 
        return EntityModel.of(dto,
                linkTo(methodOn(PagoController.class).obtenerPorId(pago.getIdPago())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listar()).withRel("todos-los-pagos"),
                linkTo(methodOn(PagoController.class).eliminar(pago.getIdPago())).withRel("eliminar")
        );
    }
}
