package cl.duoc.ms_pago.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-dueno", url = "${api.dueno.url}")
public interface DuenoClient {
    //Recordar cambiar el endpoint de dueño
    @GetMapping("/api/v1/dueno/{id}/exists")
    Boolean existeDueno(@PathVariable("id") Long id);

}
