package cl.duoc.ms_personal.service;

import cl.duoc.ms_personal.exception.ResourceNotFoundException;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.repository.PersonalRepository;
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
public class PersonalServiceTest {
 
    @Mock
    private PersonalRepository personalRepository;
 
    @InjectMocks
    private PersonalService personalService;
 
    private Personal personal;
 
    @BeforeEach
    void setUp() {
        personal = new Personal(1L, "Ana", "González", "Veterinario", "ana@mail.com", true);
    }
 
    @Test
    void testListar() {
        when(personalRepository.findAll()).thenReturn(List.of(personal));
        List<Personal> resultado = personalService.listar();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
 
    @Test
    void testGuardar() {
        when(personalRepository.save(any(Personal.class))).thenReturn(personal);
        Personal resultado = personalService.guardar(personal);
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
    }
 
    @Test
    void testFindByIdExitoso() {
        when(personalRepository.findById(1L)).thenReturn(Optional.of(personal));
        Optional<Personal> resultado = personalService.findById(1L);
        assertTrue(resultado.isPresent());
    }
 
    @Test
    void testFindByIdNoEncontrado() {
        when(personalRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Personal> resultado = personalService.findById(99L);
        assertFalse(resultado.isPresent());
    }
 
    @Test
    void testExistePorId() {
        when(personalRepository.existsById(1L)).thenReturn(true);
        assertTrue(personalService.existePorId(1L));
    }
 
    @Test
    void testActualizar() {
        when(personalRepository.findById(1L)).thenReturn(Optional.of(personal));
        when(personalRepository.save(any(Personal.class))).thenReturn(personal);
        Personal resultado = personalService.actualizar(1L, personal);
        assertNotNull(resultado);
    }
 
    @Test
    void testActualizarNoEncontrado() {
        when(personalRepository.findById(99L)).thenReturn(Optional.empty());
        // CORREGIDO: Cambiar RuntimeException por ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> personalService.actualizar(99L, personal));
    }
 
    @Test
    void testEliminarExitoso() {
        when(personalRepository.existsById(1L)).thenReturn(true);
        doNothing().when(personalRepository).deleteById(1L);
        
        assertDoesNotThrow(() -> personalService.eliminar(1L));
        verify(personalRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarNoEncontradoLanzaException() {
        when(personalRepository.existsById(99L)).thenReturn(false);
        
        // CORREGIDO: Verificar que la regla de negocio impida el borrado si no existe
        assertThrows(ResourceNotFoundException.class, () -> personalService.eliminar(99L));
        verify(personalRepository, never()).deleteById(anyLong());
    }
}