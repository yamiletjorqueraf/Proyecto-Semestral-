package cl.duoc.ms_ventas.assamblers;


import cl.duoc.ms_ventas.Controller.VentaController;
import cl.duoc.ms_ventas.Model.Venta;
import cl.duoc.ms_ventas.dto.VentaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<VentaDTO>> {
 
    @Override
    public EntityModel<VentaDTO> toModel(Venta venta) {
        VentaDTO dto = VentaDTO.fromModel(venta);
        return EntityModel.of(dto,
                linkTo(methodOn(VentaController.class).obtenerPorId(venta.getIdVenta())).withSelfRel(),
                linkTo(methodOn(VentaController.class).listar()).withRel("todas-las-ventas"),
                linkTo(methodOn(VentaController.class).eliminar(venta.getIdVenta())).withRel("eliminar")
        );
    }
}
