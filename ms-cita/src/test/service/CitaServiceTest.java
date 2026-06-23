package cl.duoc.ms_cita.Service;
 
import cl.duoc.ms_cita.Client.MascotaClient;
import cl.duoc.ms_cita.Client.PersonalClient;
import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Repository.CitaRepository;
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
public class CitaServiceTest {
 
    @Mock
    private CitaRepository citaRepository;
 
    @Mock
    private MascotaClient mascotaClient;
 
    @Mock
    private PersonalClient personalClient;
 
    @InjectMocks
    private CitaService citaService;
 
    private Cita cita;
 
    @BeforeEach
    void setUp() {
        cita = new Cita(1L, 2L, 3L, new Date(), "Control rutinario", "Pendiente");
    }
 
    @Test
    void testGuardarExitoso() {
        when(mascotaClient.existeMascota(2L)).thenReturn(true);
        when(personalClient.existePersonal(3L)).thenReturn(true);
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);
        Cita resultado = citaService.guardar(cita);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCita());
    }
 
    @Test
    void testGuardarMascotaNoExiste() {
        when(mascotaClient.existeMascota(2L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> citaService.guardar(cita));
        verify(citaRepository, never()).save(any(Cita.class));
    }
 
    @Test
    void testGuardarPersonalNoExiste() {
        when(mascotaClient.existeMascota(2L)).thenReturn(true);
        when(personalClient.existePersonal(3L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> citaService.guardar(cita));
        verify(citaRepository, never()).save(any(Cita.class));
    }
 
    @Test
    void testListar() {
        when(citaRepository.findAll()).thenReturn(List.of(cita));
        List<Cita> resultado = citaService.listar();
        assertEquals(1, resultado.size());
    }
 
    @Test
    void testFindByIdExitoso() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        Optional<Cita> resultado = citaService.findById(1L);
        assertTrue(resultado.isPresent());
    }
 
    @Test
    void testFindByIdNoEncontrado() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());
        assertFalse(citaService.findById(99L).isPresent());
    }
 
    @Test
    void testExistePorId() {
        when(citaRepository.existsById(1L)).thenReturn(true);
        assertTrue(citaService.existePorId(1L));
    }
 
    @Test
    void testEliminar() {
        doNothing().when(citaRepository).deleteById(1L);
        assertDoesNotThrow(() -> citaService.eliminar(1L));
        verify(citaRepository, times(1)).deleteById(1L);
    }
}
