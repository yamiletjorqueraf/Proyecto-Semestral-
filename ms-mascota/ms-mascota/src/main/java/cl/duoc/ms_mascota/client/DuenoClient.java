package cl.duoc.ms_mascota.client;

 import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-dueno", url = "${ms.dueno.url}/api/v1/dueno")
public interface DuenoClient {
    @GetMapping("/{id}/exists")
    boolean existeDueno(@PathVariable("id") Long id);
}
public class DuenoClient {

}
