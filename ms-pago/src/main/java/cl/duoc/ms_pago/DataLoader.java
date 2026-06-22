package cl.duoc.ms_pago;

import cl.duoc.ms_pago.model.Pago;
import cl.duoc.ms_pago.repository.PagoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
import java.util.concurrent.TimeUnit;

@Component
public class DataLoader implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
 
    private final PagoRepository pagoRepository;
 
    public DataLoader(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }
 
    @Override
    public void run(String... args) {
        if (pagoRepository.count() == 0) {
            logger.info("Cargando datos de prueba en ms-pago...");
 
            Faker faker = new Faker();
 
            String[] mediosPago = {"Efectivo", "Debito", "Credito"};
            String[] estados    = {"Completado", "Anulado", "Pendiente"};
 
            for (int i = 0; i < 10; i++) {
                int valorNeto  = faker.number().numberBetween(100, 100000);
                int descuento  = faker.number().numberBetween(0, 30);
                int subtotal   = valorNeto - (valorNeto * descuento / 100);
                int iva        = subtotal * 19 / 100;
                int totalPagar = subtotal + iva;
 
                Pago pago = new Pago(
                        null,
                        (long) faker.number().numberBetween(1, 20),   // idVenta
                        (long) faker.number().numberBetween(1, 10),   // idDueno
                        valorNeto,
                        iva,
                        descuento,
                        totalPagar,
                        mediosPago[faker.number().numberBetween(0, 3)],
                        estados[faker.number().numberBetween(0, 3)],
                        faker.date().past(365, TimeUnit.DAYS)         // fecha último año
                );
 
                pagoRepository.save(pago);
                logger.info("Pago #{} creado - Neto: {}, Dcto: {}%, Total: {}",
                        i + 1, valorNeto, descuento, totalPagar);
            }
 
            logger.info("✓ 10 pagos de prueba cargados exitosamente");
        } else {
            logger.info("La BD ya tiene datos, omitiendo carga inicial");
        }
    }

}
