package cl.duoc.ms_dueno.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "${ms.usuario.url}")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuario/{id}/exists")
    Boolean existeUsuario(@PathVariable("id") Long id);

}
