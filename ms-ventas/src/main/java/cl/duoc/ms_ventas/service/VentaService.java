package cl.duoc.ms_ventas.service;

import cl.duoc.ms_ventas.client.DuenoClient;
import cl.duoc.ms_ventas.client.MascotaClient;
import cl.duoc.ms_ventas.exception.BadRequestException;
import cl.duoc.ms_ventas.exception.ResourceNotFoundException;
import cl.duoc.ms_ventas.model.Venta;
import cl.duoc.ms_ventas.repository.VentaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
 
import java.util.Date;
import java.util.List;

@Service
public class VentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);
 
    private final VentaRepository ventaRepository;
    private final DuenoClient duenoClient;
    private final MascotaClient mascotaClient;
 
    public VentaService(VentaRepository ventaRepository, DuenoClient duenoClient, MascotaClient mascotaClient) {
        this.ventaRepository = ventaRepository;
        this.duenoClient = duenoClient;
        this.mascotaClient = mascotaClient;
    }

    public Venta guardar(Venta venta) {
        logger.info("Iniciando guardar venta: idDueno={}, idMascota={}, tipoServicio={}, monto={}",
                venta.getIdDueno(), venta.getIdMascota(), venta.getTipoServicio(), venta.getMonto());
        try {
            if (venta.getFecha() == null) venta.setFecha(new Date());
 
            validarDueno(venta.getIdDueno());
            validarMascota(venta.getIdMascota());
 
            Venta ventaGuardada = ventaRepository.save(venta);
            logger.info("Venta guardada exitosamente con id={}", ventaGuardada.getIdVenta());
            return ventaGuardada;
        } catch (Exception e) {
            logger.error("Error al guardar venta: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Venta> listar() {
        logger.info("Listando todas las ventas");
        List<Venta> ventas = ventaRepository.findAll();
        logger.info("Total ventas encontradas: {}", ventas.size());
        return ventas;
    }

    public Venta obtenerPorId(Long id) {
        logger.info("Buscando venta por id={}", id);
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Venta no encontrada id={}", id);
                    return new ResourceNotFoundException("Venta no existe");
                });
        logger.info("Venta encontrada id={}", id);
        return venta;
    }

    public boolean existePorId(Long id) {
        return ventaRepository.existsById(id);
    }

    public Venta actualizar(Long id, Venta venta) {
        logger.info("Iniciando actualizar venta id={}", id);
        try {
            Venta existente = ventaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Venta no existe"));
 
            if (venta.getFecha() == null) venta.setFecha(new Date());
 
            validarDueno(venta.getIdDueno());
            validarMascota(venta.getIdMascota());
 
            existente.setIdDueno(venta.getIdDueno());
            existente.setIdMascota(venta.getIdMascota());
            existente.setTipoServicio(venta.getTipoServicio());
            existente.setDescripcion(venta.getDescripcion());
            existente.setMonto(venta.getMonto());
            existente.setEstado(venta.getEstado());
            existente.setFecha(venta.getFecha());
 
            Venta actualizada = ventaRepository.save(existente);
            logger.info("Venta actualizada exitosamente id={}", actualizada.getIdVenta());
            return actualizada;
        } catch (Exception e) {
            logger.error("Error al actualizar venta id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public void eliminar(Long id) {
        logger.info("Iniciando eliminación de venta id={}", id);
        try {
            if (!ventaRepository.existsById(id)) {
                logger.warn("Venta no existe para eliminar id={}", id);
                throw new ResourceNotFoundException("Venta no existe");
            }
            ventaRepository.deleteById(id);
            logger.info("Venta eliminada exitosamente id={}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar venta id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    private void validarDueno(Long idDueno) {
        logger.info("Validando existencia de dueño id={}", idDueno);
        Boolean existe = duenoClient.existeDueno(idDueno);
        logger.info("Respuesta validación dueño: {}", existe);
        if (existe == null) throw new BadRequestException("No se pudo validar la existencia del dueño");
        if (Boolean.FALSE.equals(existe)) throw new ResourceNotFoundException("Dueño no existe");
    }

    private void validarMascota(Long idMascota) {
        logger.info("Validando existencia de mascota id={}", idMascota);
        Boolean existe = mascotaClient.existeMascota(idMascota);
        logger.info("Respuesta validación mascota: {}", existe);
        if (existe == null) throw new BadRequestException("No se pudo validar la existencia de la mascota");
        if (Boolean.FALSE.equals(existe)) throw new ResourceNotFoundException("Mascota no existe");
    }

}
