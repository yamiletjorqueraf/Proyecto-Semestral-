package cl.duoc.ms_dueno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDuenoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDuenoApplication.class, args);
	}

}
