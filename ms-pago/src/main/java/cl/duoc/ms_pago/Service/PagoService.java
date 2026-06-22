package cl.duoc.ms_pago.Service;

import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.duoc.ms_pago.exception.BadRequestException;
import cl.duoc.ms_pago.exception.ResourceNotFoundException;
import cl.duoc.ms_pago.Client.DuenoClient;
import cl.duoc.ms_pago.Client.VentaClient;
import cl.duoc.ms_pago.Model.Pago;
import cl.duoc.ms_pago.Repository.PagoRepository;

@Service
public class PagoService {

    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final VentaClient ventaClient;
    private final DuenoClient duenoClient;

    public PagoService(PagoRepository pagoRepository, VentaClient ventaClient, DuenoClient duenoClient) {
        this.pagoRepository = pagoRepository;
        this.ventaClient = ventaClient;
        this.duenoClient = duenoClient;
    }

    public Pago guardar(Pago pago) {
        logger.info("Iniciando guardar pago: idVenta={}, idDueno={}, Neto={}, Dcto={}, MedioPago={}",
                pago.getIdVenta(), pago.getIdDueno(), pago.getValorNeto(),
                pago.getDescuento(), pago.getMedioPago());
 
        // Calcular IVA y total antes de validar
        int subtotal = calcularSubtotal(pago.getValorNeto(), pago.getDescuento());
        pago.setIva(calcularIVA(subtotal));
        pago.setTotalPagar(subtotal + pago.getIva());
 
        try {
            if (pago.getMedioPago() == null || pago.getMedioPago().isBlank())
                throw new IllegalArgumentException("medioPago requerido");
            if (pago.getEstado() == null || pago.getEstado().isBlank())
                pago.setEstado("Pendiente");
            if (pago.getFecha() == null)
                pago.setFecha(new Date());
 
            // Validar venta
            logger.info("Validando existencia de venta id={}", pago.getIdVenta());
            Boolean existeVenta = ventaClient.existeVenta(pago.getIdVenta());
            logger.info("Respuesta validación venta: existeVenta={}", existeVenta);
 
            if (existeVenta == null) {
                logger.error("No se pudo validar la existencia de la venta");
                throw new BadRequestException("No se pudo validar la existencia de la venta");
            }
            if (Boolean.FALSE.equals(existeVenta)) {
                logger.warn("Venta no existe con id={}", pago.getIdVenta());
                throw new ResourceNotFoundException("Venta no existe");
            }
            logger.info("Validando existencia de dueño id={}", pago.getIdDueno());
            Boolean existeDueno = duenoClient.existeDueno(pago.getIdDueno());
            logger.info("Respuesta validación dueño: existeDueno={}", existeDueno);
 
            if (existeDueno == null) {
                logger.error("No se pudo validar la existencia del dueño");
                throw new BadRequestException("No se pudo validar la existencia del dueño");
            }
            if (Boolean.FALSE.equals(existeDueno)) {
                logger.warn("Dueño no existe con id={}", pago.getIdDueno());
                throw new ResourceNotFoundException("Dueño no existe");
            }
 
            Pago pagoGuardado = pagoRepository.save(pago);
            logger.info("Pago guardado exitosamente con id={}", pagoGuardado.getIdPago());
            return pagoGuardado;
 
        } catch (Exception e) {
            logger.error("Error al guardar pago: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Pago> listar() {
        logger.info("Listando todos los pagos");
        List<Pago> pagos = pagoRepository.findAll();
        logger.info("Total pagos encontrados: {}", pagos.size());
        return pagos;
    }

    public Pago obtenerPorId(Long id) {
        logger.info("Buscando pago por id={}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pago no encontrado id={}", id);
                    return new ResourceNotFoundException("Pago no existe");
                });
        logger.info("Pago encontrado id={}", id);
        return pago;
    }

    public Pago actualizar(Long id, Pago pago) {
        logger.info("Iniciando actualizar pago id={}", id);
        try {
            Pago existente = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Pago no existe"));
 
            if (pago.getMedioPago() == null || pago.getMedioPago().isBlank())
                throw new IllegalArgumentException("medioPago requerido");
            if (pago.getFecha() == null)
                pago.setFecha(new Date());
 
            // Validar venta
            logger.info("Validando existencia de venta id={}", pago.getIdVenta());
            Boolean existeVenta = ventaClient.existeVenta(pago.getIdVenta());
            logger.info("Respuesta validación venta: existeVenta={}", existeVenta);
 
            if (existeVenta == null) {
                logger.error("No se pudo validar la existencia de la venta");
                throw new BadRequestException("No se pudo validar la existencia de la venta");
            }
            if (Boolean.FALSE.equals(existeVenta)) {
                logger.warn("Venta no existe con id={}", pago.getIdVenta());
                throw new ResourceNotFoundException("Venta no existe");
            }
 
            // Validar dueño
            logger.info("Validando existencia de dueño id={}", pago.getIdDueno());
            Boolean existeDueno = duenoClient.existeDueno(pago.getIdDueno());
            logger.info("Respuesta validación dueño: existeDueno={}", existeDueno);
 
            if (existeDueno == null) {
                logger.error("No se pudo validar la existencia del dueño");
                throw new BadRequestException("No se pudo validar la existencia del dueño");
            }
            if (Boolean.FALSE.equals(existeDueno)) {
                logger.warn("Dueño no existe con id={}", pago.getIdDueno());
                throw new ResourceNotFoundException("Dueño no existe");
            }
 
            // Recalcular valores
            int subtotal = calcularSubtotal(pago.getValorNeto(), pago.getDescuento());
            int iva = calcularIVA(subtotal);
            int totalPagar = subtotal + iva;
 
            existente.setIdVenta(pago.getIdVenta());
            existente.setIdDueno(pago.getIdDueno());
            existente.setValorNeto(pago.getValorNeto());
            existente.setDescuento(pago.getDescuento());
            existente.setIva(iva);
            existente.setTotalPagar(totalPagar);
            existente.setMedioPago(pago.getMedioPago());
            existente.setEstado(pago.getEstado());
            existente.setFecha(pago.getFecha());
 
            Pago actualizado = pagoRepository.save(existente);
            logger.info("Pago actualizado exitosamente id={}", actualizado.getIdPago());
            return actualizado;
 
        } catch (Exception e) {
            logger.error("Error al actualizar pago id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public void eliminar(Long id) {
        logger.info("Iniciando eliminación de pago id={}", id);
        try {
            if (!pagoRepository.existsById(id)) {
                logger.warn("Pago no existe para eliminar id={}", id);
                throw new ResourceNotFoundException("Pago no existe");
            }
            pagoRepository.deleteById(id);
            logger.info("Pago eliminado exitosamente id={}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar pago id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public int calcularSubtotal(int neto, int porcentajeDescuento) {
        if (neto < 0)
            throw new IllegalArgumentException("El valor neto no puede ser negativo");
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100)
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        return neto - (neto * porcentajeDescuento / 100);
    }
 
    public int calcularIVA(int subtotal) {
        if (subtotal < 0)
            throw new IllegalArgumentException("El subtotal no puede ser negativo");
        return subtotal * 19 / 100;
    }
 

}
