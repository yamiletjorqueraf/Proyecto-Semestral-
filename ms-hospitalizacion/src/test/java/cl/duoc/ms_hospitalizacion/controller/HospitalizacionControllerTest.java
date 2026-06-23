package cl.duoc.ms_hospitalizacion.controller;

import cl.duoc.ms_hospitalizacion.assamblers.HospitalizacionModelAssembler;
import cl.duoc.ms_hospitalizacion.dto.HospitalizacionDTO;
import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;
import cl.duoc.ms_hospitalizacion.service.HospitalizacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(HospitalizacionController.class)
class HospitalizacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private HospitalizacionService hospitalizacionService;

    @Mock
    private HospitalizacionModelAssembler assembler;

    private Hospitalizacion hospitalizacion;
    private HospitalizacionDTO dto;

    @BeforeEach
    void setUp() {
        LocalDate inicio = LocalDate.now();
        LocalDate alta = LocalDate.now().plusDays(2);
        hospitalizacion = new Hospitalizacion(1L, 10L, 20L, inicio, alta, "Control");
        dto = new HospitalizacionDTO(1L, 10L,20L, inicio, alta, "Control");
    }

    @Test
    void cuandoObtenerPorId_yExiste_entoncesRetorna200() throws Exception {
        when(hospitalizacionService.findById(1L)).thenReturn(Optional.of(hospitalizacion));
        when(assembler.toModel(any(Hospitalizacion.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/hospitalizacion/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnostico").value("Control"));
    }
}