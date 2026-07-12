package cl.duoc.ms_resultados_examenes.service;

import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.exception.ResourceNotFoundException;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import cl.duoc.ms_resultados_examenes.repository.ResultadoExamenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultadoExamenServiceTest {

    @Mock
    private ResultadoExamenRepository resultadoExamenRepository;

    @InjectMocks
    private ResultadoExamenService resultadoExamenService;

    private ResultadoExamen examen;
    private ResultadoExamenDTO dto;

    @BeforeEach
    void setUp() {
        examen = new ResultadoExamen(1L, 10L, "Sangre", LocalDate.now(), "Normal", 5L, "Dr. Pérez");
        dto = new ResultadoExamenDTO(1L, 10L, "Sangre", LocalDate.now(), "Normal", 5L, "Dr. Pérez");
    }

    @Test
    void cuandoGuardar_entoncesRetornaExamenGuardado() {
        when(resultadoExamenRepository.save(any(ResultadoExamen.class))).thenReturn(examen);

        ResultadoExamen resultado = resultadoExamenService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Sangre", resultado.getTipoExamen());
        verify(resultadoExamenRepository, times(1)).save(any(ResultadoExamen.class));
    }

    @Test
    void cuandoBuscarPorIdExistente_entoncesRetornaExamen() {
        when(resultadoExamenRepository.findById(1L)).thenReturn(Optional.of(examen));

        Optional<ResultadoExamen> resultado = resultadoExamenService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdResultado());
    }

    @Test
    void cuandoBuscarPorIdInexistente_entoncesRetornaOptionalVacio() {
        when(resultadoExamenRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ResultadoExamen> resultado = resultadoExamenService.findById(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void cuandoEliminarIdExistente_entoncesEliminaCorrectamente() {
        when(resultadoExamenRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resultadoExamenRepository).deleteById(1L);

        assertDoesNotThrow(() -> resultadoExamenService.eliminar(1L));
        verify(resultadoExamenRepository, times(1)).deleteById(1L);
    }

    @Test
    void cuandoEliminarIdInexistente_entoncesLanzaResourceNotFoundException() {
        // CORREGIDO: Garantizar la validación de excepciones personalizadas de negocio
        when(resultadoExamenRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> resultadoExamenService.eliminar(99L));
        verify(resultadoExamenRepository, never()).deleteById(anyLong());
    }
}