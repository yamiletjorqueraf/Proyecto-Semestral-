package cl.duoc.ms_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-mascota", url = "${api.mascota.url}")
public interface MascotaClient {
    
    @GetMapping("/api/v1/mascotas/{id}/exists")
    Boolean existeMascota(@PathVariable("id") Long id);

}
