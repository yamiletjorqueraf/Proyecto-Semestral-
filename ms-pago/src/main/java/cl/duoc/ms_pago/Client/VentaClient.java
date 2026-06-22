package cl.duoc.ms_pago.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-venta", url = "${api.venta.url}")
public interface VentaClient {
    //Recordar cambiar el endpoint de venta
    @GetMapping("/api/v1/ventas/{id}/exists")
    Boolean existeVenta(@PathVariable("id") Long id);

}
