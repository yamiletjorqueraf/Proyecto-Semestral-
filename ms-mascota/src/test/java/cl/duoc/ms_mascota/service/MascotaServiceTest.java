package cl.duoc.ms_mascota.service;

import cl.duoc.ms_mascota.client.DuenoClient;
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
        assertThrows(RuntimeException.class, () -> mascotaService.guardar(mascota));
        verify(mascotaRepository, never()).save(any(Mascota.class));
    }
 
    @Test
    void testListar() {
        when(mascotaRepository.findAll()).thenReturn(List.of(mascota));
        List<Mascota> resultado = mascotaService.listar();
        assertEquals(1, resultado.size());
    }
 
    @Test
    void testFindByIdExitoso() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        Optional<Mascota> resultado = mascotaService.findById(1L);
        assertTrue(resultado.isPresent());
    }
 
    @Test
    void testFindByIdNoEncontrado() {
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());
        assertFalse(mascotaService.findById(99L).isPresent());
    }
 
    @Test
    void testExistePorId() {
        when(mascotaRepository.existsById(1L)).thenReturn(true);
        assertTrue(mascotaService.existePorId(1L));
    }
 
    @Test
    void testEliminar() {
        doNothing().when(mascotaRepository).deleteById(1L);
        assertDoesNotThrow(() -> mascotaService.eliminar(1L));
        verify(mascotaRepository, times(1)).deleteById(1L);
    }
}
