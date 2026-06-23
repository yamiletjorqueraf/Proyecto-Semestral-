package cl.duoc.ms_ventas.service;

import cl.duoc.ms_ventas.client.DuenoClient;
import cl.duoc.ms_ventas.client.MascotaClient;
import cl.duoc.ms_ventas.exception.BadRequestException;
import cl.duoc.ms_ventas.exception.ResourceNotFoundException;
import cl.duoc.ms_ventas.model.Venta;
import cl.duoc.ms_ventas.repository.VentaRepository;


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
public class VentaServiceTest {
     @Mock
    private VentaRepository ventaRepository;
 
    @Mock
    private DuenoClient duenoClient;
 
    @Mock
    private MascotaClient mascotaClient;
 
    @InjectMocks
    private VentaService ventaService;
 
    private Venta venta;
 
    @BeforeEach
    void setUp() {
        venta = new Venta(1L, 2L, 3L, "Consulta",
                "Revisión general", 15000, "Completado", new Date());
    }
 
    
    @Test
    void testGuardarVentaExitoso() {
        when(duenoClient.existeDueno(2L)).thenReturn(true);
        when(mascotaClient.existeMascota(3L)).thenReturn(true);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
 
        Venta resultado = ventaService.guardar(venta);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVenta());
        assertEquals("Consulta", resultado.getTipoServicio());
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }
 
    @Test
    void testGuardarVentaDuenoNoExiste() {
        when(duenoClient.existeDueno(2L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> ventaService.guardar(venta));
 
        verify(ventaRepository, never()).save(any(Venta.class));
    }
 
    @Test
    void testGuardarVentaMascotaNoExiste() {
        when(duenoClient.existeDueno(2L)).thenReturn(true);
        when(mascotaClient.existeMascota(3L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> ventaService.guardar(venta));
 
        verify(ventaRepository, never()).save(any(Venta.class));
    }
 
    @Test
    void testGuardarVentaDuenoNull() {
        when(duenoClient.existeDueno(2L)).thenReturn(null);
 
        assertThrows(BadRequestException.class,
                () -> ventaService.guardar(venta));
 
        verify(ventaRepository, never()).save(any(Venta.class));
    }
 
    
    @Test
    void testListar() {
        when(ventaRepository.findAll()).thenReturn(List.of(venta));
 
        List<Venta> resultado = ventaService.listar();
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ventaRepository, times(1)).findAll();
    }
 
    
    @Test
    void testObtenerPorIdExitoso() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
 
        Venta resultado = ventaService.obtenerPorId(1L);
 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVenta());
    }
 
    @Test
    void testObtenerPorIdNoEncontrado() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());
 
        assertThrows(ResourceNotFoundException.class,
                () -> ventaService.obtenerPorId(99L));
    }
 
    
    @Test
    void testEliminarExitoso() {
        when(ventaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ventaRepository).deleteById(1L);
 
        assertDoesNotThrow(() -> ventaService.eliminar(1L));
 
        verify(ventaRepository, times(1)).deleteById(1L);
    }
 
    @Test
    void testEliminarNoEncontrado() {
        when(ventaRepository.existsById(99L)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class,
                () -> ventaService.eliminar(99L));
 
        verify(ventaRepository, never()).deleteById(any());
    }

}
