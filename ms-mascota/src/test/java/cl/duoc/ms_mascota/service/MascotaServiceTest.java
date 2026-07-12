package cl.duoc.ms_mascota.service;

import cl.duoc.ms_mascota.client.DuenoClient;
import cl.duoc.ms_mascota.exception.BadRequestException;
import cl.duoc.ms_mascota.exception.ResourceNotFoundException;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {
 
    @Mock
    private MascotaRepository mascotaRepository;
 
    @Mock
    private DuenoClient duenoClient;
 
    @InjectMocks
    private MascotaService mascotaService;
 
    private Mascota mascota;
 
    @BeforeEach
    void setUp() {
        mascota = new Mascota(1L, "Firulais", "Perro", "Labrador", 3, 2L);
    }
 
    @Test
    void testGuardarExitoso() {
        when(duenoClient.existeDueno(2L)).thenReturn(true);
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);
        
        Mascota resultado = mascotaService.guardar(mascota);
        
        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getNombre());
    }
 
    @Test
    void testGuardarDuenoNoExiste() {
        when(duenoClient.existeDueno(2L)).thenReturn(false);
        
        // CORREGIDO: Verificar la excepción BadRequestException específica
        assertThrows(BadRequestException.class, () -> mascotaService.guardar(mascota));
        verify(mascotaRepository, never()).save(any(Mascota.class));
    }
 
    @Test
    void testActualizarMascotaNoExiste() {
        Mascota nuevosDatos = new Mascota(1L, "NuevoNombre", "Perro", "Pug", 4, 2L);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.empty());

        // CORREGIDO: Verificar que lanza ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> mascotaService.actualizar(1L, nuevosDatos));
    }

    @Test
    void testActualizarSinCambioDeDuenoNoLlamaAFeign() {
        Mascota nuevosDatos = new Mascota(1L, "Firulais Editado", "Perro", "Labrador", 4, 2L); // Mismo idDueno (2L)
        
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(nuevosDatos);

        Mascota resultado = mascotaService.actualizar(1L, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("Firulais Editado", resultado.getNombre());
        verify(duenoClient, never()).existeDueno(anyLong());
    }
 
    @Test
    void testListar() {
        when(mascotaRepository.findAll()).thenReturn(List.of(mascota));
        List<Mascota> resultado = mascotaService.listar();
        assertEquals(1, resultado.size());
    }
 
    @Test
    void testEliminarExitoso() {
        when(mascotaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(mascotaRepository).deleteById(1L);
        
        assertDoesNotThrow(() -> mascotaService.eliminar(1L));
        verify(mascotaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarNoExistenteLanzaException() {
        when(mascotaRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> mascotaService.eliminar(99L));
        verify(mascotaRepository, never()).deleteById(99L);
    }
}