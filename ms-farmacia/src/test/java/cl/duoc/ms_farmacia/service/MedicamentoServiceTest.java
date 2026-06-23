package cl.duoc.ms_farmacia.service;

import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.exception.ResourceNotFoundException;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.repository.MedicamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



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
    void cuandoGuardarMedicamento_entoncesRetornaGuardado() {
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento resultado = medicamentoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Paracetamol", resultado.getNombre());
        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
    }

    @Test
    void cuandoEliminarInexistente_entoncesLanzaException() {
        when(medicamentoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            medicamentoService.eliminar(99L);
        });
    }
}