package cl.duoc.ms_dueno.service;

import cl.duoc.ms_dueno.Client.MascotaClient;
import cl.duoc.ms_dueno.Client.UsuarioClient;
import cl.duoc.ms_dueno.Model.Dueno;
import cl.duoc.ms_dueno.Repository.DuenoRepository;
import cl.duoc.ms_dueno.Service.DuenoService;

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
public class DuenoServiceTest {
 
    @Mock
    private DuenoRepository duenoRepository;
 
    @Mock
    private UsuarioClient usuarioClient;
 
    @Mock
    private MascotaClient mascotaClient;
 
    @InjectMocks
    private DuenoService duenoService;
 
    private Dueno dueno;
 
    @BeforeEach
    void setUp() {
        dueno = new Dueno(1L,2L, "Carlos", "López", "12345678-9", "carlos@mail.com", 912345678L, "Av. Principal 123");
    }
 
    @Test
    void testListar() {
        when(duenoRepository.findAll()).thenReturn(List.of(dueno));
        List<Dueno> resultado = duenoService.listar();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
 
    @Test
    void testGuardar() {
        when(duenoRepository.save(any(Dueno.class))).thenReturn(dueno);
        Dueno resultado = duenoService.guardar(dueno);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }
 
    @Test
    void testFindByIdExitoso() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(dueno));
        Optional<Dueno> resultado = duenoService.findById(1L);
        assertTrue(resultado.isPresent());
    }
 
    @Test
    void testFindByIdNoEncontrado() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Dueno> resultado = duenoService.findById(99L);
        assertFalse(resultado.isPresent());
    }
 
    @Test
    void testExistePorId() {
        when(duenoRepository.existsById(1L)).thenReturn(true);
        assertTrue(duenoService.existePorId(1L));
    }
 
    @Test
    void testActualizar() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(dueno));
        when(duenoRepository.save(any(Dueno.class))).thenReturn(dueno);
        Dueno resultado = duenoService.actualizar(1L, dueno);
        assertNotNull(resultado);
    }
 
    @Test
    void testActualizarNoEncontrado() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> duenoService.actualizar(99L, dueno));
    }
 
    @Test
    void testEliminar() {
        doNothing().when(duenoRepository).deleteById(1L);
        assertDoesNotThrow(() -> duenoService.eliminar(1L));
        verify(duenoRepository, times(1)).deleteById(1L);
    }
}
