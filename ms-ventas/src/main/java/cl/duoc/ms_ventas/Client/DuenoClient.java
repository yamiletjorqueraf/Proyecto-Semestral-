package cl.duoc.ms_ventas.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-dueno", url = "${api.dueno.url}")
public interface DuenoClient {
    @GetMapping("/api/v1/duenos/{id}/exists")
    Boolean existeDueno(@PathVariable("id") Long id);

}
