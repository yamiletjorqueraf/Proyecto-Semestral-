package cl.duoc.ms_farmacia.controller;

import cl.duoc.ms_farmacia.assamblers.MedicamentoModelAssembler;
import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.service.MedicamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MedicamentoController.class)
class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private MedicamentoService medicamentoService;

    @Mock
    private MedicamentoModelAssembler assembler;

    private Medicamento medicamento;
    private MedicamentoDTO dto;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento(1L, "Antiparasitario", "Uso interno", 4500.0, 20);
        dto = new MedicamentoDTO(1L, "Antiparasitario", "Uso interno", 4500.0, 20);
    }

    @Test
    void cuandoBuscarPorId_yExiste_entoncesRetornaMedicamento() throws Exception {
        when(medicamentoService.findById(1L)).thenReturn(Optional.of(medicamento));
        when(assembler.toModel(any(Medicamento.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v1/medicamento/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Antiparasitario"))
                .andExpect(jsonPath("$.stock").value(20));
    }
}