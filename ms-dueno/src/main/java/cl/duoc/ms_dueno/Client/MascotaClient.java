package cl.duoc.ms_dueno.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-mascota", url = "${ms.mascota.url}")
public interface MascotaClient {
    @GetMapping("/api/v1/mascota/{id}/exists")
    Boolean existeMascota(@PathVariable("id") Long id);

}
