package cl.duoc.ms_dueno.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "${ms.usuario.url}")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/existe/{id}")
    Boolean existeUsuario(@PathVariable("id") Long id);
}
