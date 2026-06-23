package cl.duoc.ms_resultados_examenes.controller;

import cl.duoc.ms_resultados_examenes.assamblers.ResultadoExamenModelAssembler;
import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import cl.duoc.ms_resultados_examenes.service.ResultadoExamenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ResultadoExamenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ResultadoExamenService resultadoExamenService;

    @Mock
    private ResultadoExamenModelAssembler assembler;

    private ResultadoExamen examen;
    private ResultadoExamenDTO dto;

    @BeforeEach
    void setUp() {
        examen = new ResultadoExamen(1L, 10L, "Ecografía", LocalDate.now(), "Sin hallazgos", 3L, "Dra. Gomez");
        dto = new ResultadoExamenDTO(1L, 10L, "Ecografía", LocalDate.now(), "Sin hallazgos", 3L, "Dra. Gomez");
    }

    @Test
    void cuandoObtenerPorId_yExiste_entoncesRetorna200YJson() throws Exception {
        when(resultadoExamenService.findById(1L)).thenReturn(Optional.of(examen));
        when(assembler.toModel(any(ResultadoExamen.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/resultado-examen/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoExamen").value("Ecografía"))
                .andExpect(jsonPath("$.resultado").value("Sin hallazgos"));
    }

    @Test
    void cuandoCrearResultado_entoncesRetorna21Created() throws Exception {
        when(resultadoExamenService.guardar(any(ResultadoExamenDTO.class))).thenReturn(examen);
        when(assembler.toModel(any(ResultadoExamen.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(post("/api/v1/resultado-examen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalCargo").value("Dra. Gomez"));
    }

    @Test
    void cuandoObtenerPorId_yNoExiste_entoncesRetorna404NotFound() throws Exception {
        when(resultadoExamenService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/resultado-examen/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}