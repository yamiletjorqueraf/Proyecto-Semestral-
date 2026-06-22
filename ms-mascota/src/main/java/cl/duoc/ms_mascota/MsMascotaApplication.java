package cl.duoc.ms_mascota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsMascotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMascotaApplication.class, args);
	}

}
