package cl.duoc.ms_ventas;

import cl.duoc.ms_ventas.model.Venta;
import cl.duoc.ms_ventas.repository.VentaRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
import java.util.concurrent.TimeUnit;
 
@Component
public class DataLoader implements CommandLineRunner {
 
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
 
    private final VentaRepository ventaRepository;
 
    public DataLoader(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
 
    @Override
    public void run(String... args) {
        if (ventaRepository.count() == 0) {
            logger.info("Cargando datos de prueba en ms-ventas...");
 
            Faker faker = new Faker();
 
            String[] tiposServicio = {"Consulta", "Vacuna", "Cirugía", "Medicamento"};
            String[] estados       = {"Completado", "Pendiente", "Anulado"};
 
            for (int i = 0; i < 10; i++) {
                String tipo = tiposServicio[faker.number().numberBetween(0, 4)];
                Venta venta = new Venta(
                        null,
                        (long) faker.number().numberBetween(1, 10),   // idDueno
                        (long) faker.number().numberBetween(1, 20),   // idMascota
                        tipo,
                        faker.lorem().sentence(5),                    // descripcion
                        faker.number().numberBetween(100, 500000),    // monto
                        estados[faker.number().numberBetween(0, 3)],  // estado
                        faker.date().past(365, TimeUnit.DAYS)         // fecha
                );
 
                ventaRepository.save(venta);
                logger.info("Venta #{} creada - Tipo: {}, Monto: {}",
                        i + 1, tipo, venta.getMonto());
            }
 
            logger.info("✓ 10 ventas de prueba cargadas exitosamente");
        } else {
            logger.info("La BD ya tiene datos, omitiendo carga inicial");
        }
    }
}