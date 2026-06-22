package cl.duoc.ms_cita.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-personal", url = "${ms.personal.url}")

public interface PersonalClient {
     @GetMapping("/api/v1/personal/{id}/exists")
     Boolean existePersonal(@PathVariable("id") Long id);

}
