package cl.duoc.ms_mascota.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-dueno", url = "${ms.dueno.url}")
public interface DuenoClient {
    @GetMapping("/api/v1/dueno/{id}/exists")
    boolean existeDueno(@PathVariable("id") Long id);
}

