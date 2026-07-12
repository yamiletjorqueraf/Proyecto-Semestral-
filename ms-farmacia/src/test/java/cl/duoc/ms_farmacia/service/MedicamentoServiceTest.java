package cl.duoc.ms_farmacia.service;

import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.exception.BadRequestException;
import cl.duoc.ms_farmacia.exception.ResourceNotFoundException;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.repository.MedicamentoRepository;
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
class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoService medicamentoService;

    private Medicamento medicamento;
    private MedicamentoDTO dto;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento(1L, "Paracetamol", "Antipirético", 1500.0, 50);
        dto = new MedicamentoDTO(1L, "Paracetamol", "Antipirético", 1500.0, 50);
    }

    @Test
    void cuandoGuardarMedicamentoValido_entoncesRetornaMedicamentoGuardado() {
        // GIVEN
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);

        // WHEN
        Medicamento resultado = medicamentoService.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Paracetamol", resultado.getNombre());
        assertEquals(1500.0, resultado.getPrecio());
        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
    }

    @Test
    void cuandoGuardarMedicamentoConPrecioNegativo_entoncesLanzaBadRequestException() {
        // GIVEN
        MedicamentoDTO dtoInvalido = new MedicamentoDTO(null, "Ibuprofeno", "Analgésico", -500.0, 10);

        // WHEN & THEN
        assertThrows(BadRequestException.class, () -> {
            medicamentoService.guardar(dtoInvalido);
        });
        verify(medicamentoRepository, never()).save(any(Medicamento.class));
    }

    @Test
    void cuandoListarMedicamentos_entoncesRetornaListaCompleta() {
        // GIVEN
        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));

        // WHEN
        List<Medicamento> resultado = medicamentoService.listar();

        // THEN
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void cuandoBuscarPorIdYExiste_entoncesRetornaOpcionalConMedicamento() {
        // GIVEN
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));

        // WHEN
        Optional<Medicamento> resultado = medicamentoService.findById(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Paracetamol", resultado.get().getNombre());
    }

    @Test
    void cuandoActualizarYIdNoExiste_entoncesLanzaResourceNotFoundException() {
        // GIVEN
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());
        Medicamento nuevosDatos = new Medicamento(null, "Clonazepam", "Ansiolítico", 3000.0, 5);

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            medicamentoService.actualizar(99L, nuevosDatos);
        });
    }

    @Test
    void cuandoEliminarYIdNoExiste_entoncesLanzaResourceNotFoundException() {
        // GIVEN
        when(medicamentoRepository.existsById(99L)).thenReturn(false);

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            medicamentoService.eliminar(99L);
        });
        verify(medicamentoRepository, never()).deleteById(anyLong());
    }

    @Test
    void cuandoEliminarYIdExiste_entoncesEliminaCorrectamente() {
        // GIVEN
        when(medicamentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(medicamentoRepository).deleteById(1L);

        // WHEN
        medicamentoService.eliminar(1L);

        // THEN
        verify(medicamentoRepository, times(1)).deleteById(1L);
    }
}