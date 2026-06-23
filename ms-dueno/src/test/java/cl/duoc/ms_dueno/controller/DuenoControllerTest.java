package cl.duoc.ms_dueno.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cl.duoc.ms_dueno.controller.DuenoController;
import cl.duoc.ms_dueno.model.Dueno;
import cl.duoc.ms_dueno.service.DuenoService;
import cl.duoc.ms_dueno.assamblers.DuenoModelAssembler;
import cl.duoc.ms_dueno.dto.DuenoDTO;
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
public class DuenoControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private DuenoService duenoService;
 
    @Mock
    private DuenoModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    private Dueno dueno;
    private DuenoDTO duenoDto;
 
    @BeforeEach
    void setUp() {
        dueno = new Dueno(1L,2L, "Carlos", "López", "12345678-9", "carlos@mail.com", 912345678L, "Av. Principal 123");
        duenoDto = new DuenoDTO(null,2L, "Carlos", "López", "12345678-9", "carlos@mail.com", 912345678L, "Av. Principal 123");
 
        when(assembler.toModel(any(Dueno.class)))
                .thenReturn(EntityModel.of(DuenoDTO.fromModel(dueno)));
 
        DuenoController controller = new DuenoController(duenoService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarDuenos() throws Exception {
        when(duenoService.listar()).thenReturn(List.of(dueno));
        mockMvc.perform(get("/api/v1/dueno")).andExpect(status().isOk());
    }
 
    @Test
    public void testBuscarPorId() throws Exception {
        when(duenoService.findById(1L)).thenReturn(Optional.of(dueno));
        mockMvc.perform(get("/api/v1/dueno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDueno").value(1))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }
 
    @Test
    public void testCrearDueno() throws Exception {
        when(duenoService.guardar(any(Dueno.class))).thenReturn(dueno);
        mockMvc.perform(post("/api/v1/dueno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duenoDto)))
                .andExpect(status().isCreated());
    }
 
    @Test
    public void testActualizar() throws Exception {
        when(duenoService.actualizar(eq(1L), any(Dueno.class))).thenReturn(dueno);
        mockMvc.perform(put("/api/v1/dueno/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duenoDto)))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testEliminar() throws Exception {
        doNothing().when(duenoService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/dueno/1")).andExpect(status().isOk());
        verify(duenoService, times(1)).eliminar(1L);
    }
}
