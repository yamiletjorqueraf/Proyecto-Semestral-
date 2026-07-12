package cl.duoc.ms_hospitalizacion.service;

import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.exception.BadRequestException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        LocalDate inicio = LocalDate.of(2026, 7, 1);
        LocalDate alta = LocalDate.of(2026, 7, 10);
        hospitalizacion = new Hospitalizacion(1L, 10L, 5L, inicio, alta, "Observación Canina");
        dto = new HospitalizacionDTO(1L, 10L, 5L, inicio, alta, "Observación Canina");
    }

    @Test
    void cuandoGuardarHospitalizacionValida_entoncesRetornaRegistroGuardado() {
        // GIVEN
        when(hospitalizacionRepository.save(any(Hospitalizacion.class))).thenReturn(hospitalizacion);

        // WHEN
        Hospitalizacion resultado = hospitalizacionService.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Observación Canina", resultado.getDiagnostico());
        verify(hospitalizacionRepository, times(1)).save(any(Hospitalizacion.class));
    }

    @Test
    void cuandoFechaAltaEsAnteriorAInicio_entoncesLanzaBadRequestException() {
        // GIVEN: El alta ocurre antes que el ingreso (Error de consistencia)
        HospitalizacionDTO dtoInvalido = new HospitalizacionDTO(null, 10L, 5L, 
                LocalDate.of(2026, 7, 10), 
                LocalDate.of(2026, 7, 1), 
                "Error Clínico");

        // WHEN & THEN
        assertThrows(BadRequestException.class, () -> {
            hospitalizacionService.guardar(dtoInvalido);
        });
        verify(hospitalizacionRepository, never()).save(any(Hospitalizacion.class));
    }

    @Test
    void cuandoActualizarYIdNoExiste_entoncesLanzaResourceNotFoundException() {
        // GIVEN
        when(hospitalizacionRepository.findById(99L)).thenReturn(Optional.empty());
        Hospitalizacion nuevosDatos = new Hospitalizacion(null, 10L, 5L, LocalDate.now(), LocalDate.now(), "Cirugía");

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            hospitalizacionService.actualizar(99L, nuevosDatos);
        });
    }
}