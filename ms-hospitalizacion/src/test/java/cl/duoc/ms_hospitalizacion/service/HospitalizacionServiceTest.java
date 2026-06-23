package cl.duoc.ms_hospitalizacion.service;

import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.exception.ResourceNotFoundException;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.repository.HospitalizacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HospitalizacionServiceTest {

    @Mock
    private HospitalizacionRepository hospitalizacionRepository;

    @InjectMocks
    private HospitalizacionService hospitalizacionService;

    private Hospitalizacion hospitalizacion;
    private HospitalizacionDTO dto;

    @BeforeEach
    void setUp() {
        LocalDate inicio = LocalDate.now();
        LocalDate alta = LocalDate.now().plusDays(3);
        hospitalizacion = new Hospitalizacion(1L, 10L, 20L, inicio, alta, "Post-operatorio");
        dto = new HospitalizacionDTO(1L, 10L, 20L, inicio,alta, "Post-operatorio");
    }

    @Test
    void cuandoGuardar_entoncesRetornaRegistroGuardado() {
        when(hospitalizacionRepository.save(any(Hospitalizacion.class))).thenReturn(hospitalizacion);

        Hospitalizacion resultado = hospitalizacionService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Post-operatorio", resultado.getDiagnostico());
        verify(hospitalizacionRepository, times(1)).save(any(Hospitalizacion.class));
    }

    @Test
    void cuandoBuscarPorIdInexistente_entoncesLanzaException() {
        when(hospitalizacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            hospitalizacionService.actualizar(99L, hospitalizacion);
        });
    }
}