package cl.duoc.ms_pago;


import cl.duoc.ms_pago.client.DuenoClient;
import cl.duoc.ms_pago.client.VentaClient;
import cl.duoc.ms_pago.exception.BadRequestException;
import cl.duoc.ms_pago.exception.ResourceNotFoundException;
import cl.duoc.ms_pago.model.Pago;
import cl.duoc.ms_pago.repository.PagoRepository;
import cl.duoc.ms_pago.service.PagoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.Date;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MsPagoApplicationTests {

	@Mock
    private PagoRepository pagoRepository;
 
    @Mock
    private VentaClient ventaClient;
 
    @Mock
    private DuenoClient duenoClient;
 
    @InjectMocks
    private PagoService pagoService;
 
    private Pago pago;
 
    @BeforeEach
    void setUp() {
        pago = new Pago(
                1L,
                2L,
                3L,
                1000,
                190,
                10,
                1090,
                "Tarjeta",
                "Completado",
                new Date()
        );
    }
 
    // ─── CÁLCULOS ─────────────────────────────────────────────
 
    @Test
    void testCalcularSubtotalSinDescuento() {
        int resultado = pagoService.calcularSubtotal(1000, 0);
        assertEquals(1000, resultado);
    }
 
    @Test
    void testCalcularSubtotalConDescuento() {
        int resultado = pagoService.calcularSubtotal(1000, 10);
        assertEquals(900, resultado);
    }
 
    @Test
    void testCalcularSubtotalDescuentoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> pagoService.calcularSubtotal(1000, 150));
    }
 
    @Test
    void testCalcularIVA() {
        int resultado = pagoService.calcularIVA(1000);
        assertEquals(190, resultado);
    }
 
    @Test
    void testCalcularIVANegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> pagoService.calcularIVA(-100));
    }
 
    // ─── GUARDAR ──────────────────────────────────────────────
 
    @Test
    void testGuardarPagoExitoso() {
        when(ventaClient.existeVenta(2L)).thenReturn(true);
        when(duenoClient.existeDueno(3L)).thenReturn(true);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
 
        Pago resultado = pagoService.guardar(pago);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
        assertEquals("Tarjeta", resultado.getMedioPago());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }
 
    @Test
    void testGuardarPagoVentaNoExiste() {
        when(ventaClient.existeVenta(2L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> pagoService.guardar(pago));
 
        verify(pagoRepository, never()).save(any(Pago.class));
    }
 
    @Test
    void testGuardarPagoDuenoNoExiste() {
        when(ventaClient.existeVenta(2L)).thenReturn(true);
        when(duenoClient.existeDueno(3L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> pagoService.guardar(pago));
 
        verify(pagoRepository, never()).save(any(Pago.class));
    }
 
    @Test
    void testGuardarPagoVentaNull() {
        when(ventaClient.existeVenta(2L)).thenReturn(null);
 
        assertThrows(BadRequestException.class,
                () -> pagoService.guardar(pago));
 
        verify(pagoRepository, never()).save(any(Pago.class));
    }
 
    // ─── LISTAR ───────────────────────────────────────────────
 
    @Test
    void testListar() {
        when(pagoRepository.findAll()).thenReturn(List.of(pago));
 
        List<Pago> resultado = pagoService.listar();
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pagoRepository, times(1)).findAll();
    }
 
    // ─── OBTENER POR ID ───────────────────────────────────────
 
    @Test
    void testObtenerPorIdExitoso() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
 
        Pago resultado = pagoService.obtenerPorId(1L);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
    }
 
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
 
        assertThrows(ResourceNotFoundException.class,
                () -> pagoService.obtenerPorId(99L));
    }
 
    // ─── ELIMINAR ─────────────────────────────────────────────
 
    @Test
    void testEliminarExitoso() {
        when(pagoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(1L);
 
        assertDoesNotThrow(() -> pagoService.eliminar(1L));
 
        verify(pagoRepository, times(1)).deleteById(1L);
    }
 
    @Test
    void testEliminarNoEncontrado() {
        when(pagoRepository.existsById(99L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> pagoService.eliminar(99L));
 
        verify(pagoRepository, never()).deleteById(any());
    }

}
