package cl.duoc.ms_personal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 
import cl.duoc.ms_personal.assamblers.PersonalModelAssembler;
import cl.duoc.ms_personal.dto.PersonalDTO;
import cl.duoc.ms_personal.model.Personal;
import cl.duoc.ms_personal.service.PersonalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import java.util.List;
import java.util.Optional;
 
@ExtendWith(MockitoExtension.class)
public class PersonalControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private PersonalService personalService;
 
    @Mock
    private PersonalModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    private Personal personal;
    private PersonalDTO personalDto;
 
    @BeforeEach
    void setUp() {
        personal = new Personal(1L, "Ana", "González", "Veterinario", "ana@mail.com", true);
        personalDto = new PersonalDTO(null, "Ana", "González", "Veterinario", "ana@mail.com", true);
 
        when(assembler.toModel(any(Personal.class)))
                .thenReturn(EntityModel.of(PersonalDTO.fromModel(personal)));
 
        PersonalController controller = new PersonalController(personalService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarPersonal() throws Exception {
        when(personalService.listar()).thenReturn(List.of(personal));
        mockMvc.perform(get("/api/v1/personal")).andExpect(status().isOk());
    }
 
    @Test
    public void testBuscarPorId() throws Exception {
        when(personalService.findById(1L)).thenReturn(Optional.of(personal));
        mockMvc.perform(get("/api/v1/personal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPersonal").value(1))
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }
 
    @Test
    public void testCrearPersonal() throws Exception {
        when(personalService.guardar(any(Personal.class))).thenReturn(personal);
        mockMvc.perform(post("/api/v1/personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personalDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }
 
    @Test
    public void testActualizar() throws Exception {
        when(personalService.actualizar(eq(1L), any(Personal.class))).thenReturn(personal);
        mockMvc.perform(put("/api/v1/personal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personalDto)))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testEliminar() throws Exception {
        doNothing().when(personalService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/personal/1")).andExpect(status().isOk());
        verify(personalService, times(1)).eliminar(1L);
    }
}
