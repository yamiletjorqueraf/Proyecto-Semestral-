package cl.duoc.ms_cita.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 
import cl.duoc.ms_cita.Model.Cita;
import cl.duoc.ms_cita.Service.CitaService;
import cl.duoc.ms_cita.assamblers.CitaModelAssembler;
import cl.duoc.ms_cita.dto.CitaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import java.util.Date;
import java.util.List;
import java.util.Optional;
 
@ExtendWith(MockitoExtension.class)
public class CitaControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private CitaService citaService;
 
    @Mock
    private CitaModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
 
    private Cita cita;
    private CitaDTO citaDto;
 
    @BeforeEach
    void setUp() {
        cita = new Cita(1L, 2L, 3L, new Date(), "Control rutinario", "Pendiente");
        citaDto = new CitaDTO(null, 2L, 3L, new Date(), "Control rutinario", "Pendiente");
 
        when(assembler.toModel(any(Cita.class)))
                .thenReturn(EntityModel.of(CitaDTO.fromModel(cita)));
 
        CitaController controller = new CitaController(citaService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarCitas() throws Exception {
        when(citaService.listar()).thenReturn(List.of(cita));
        mockMvc.perform(get("/api/v1/cita")).andExpect(status().isOk());
    }
 
    @Test
    public void testBuscarPorId() throws Exception {
        when(citaService.findById(1L)).thenReturn(Optional.of(cita));
        mockMvc.perform(get("/api/v1/cita/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCita").value(1))
                .andExpect(jsonPath("$.motivo").value("Control rutinario"));
    }
 
    @Test
    public void testCrearCita() throws Exception {
        when(citaService.guardar(any(Cita.class))).thenReturn(cita);
        mockMvc.perform(post("/api/v1/cita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaDto)))
                .andExpect(status().isCreated());
    }
 
    @Test
    public void testActualizar() throws Exception {
        when(citaService.actualizar(eq(1L), any(Cita.class))).thenReturn(cita);
        mockMvc.perform(put("/api/v1/cita/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaDto)))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testEliminar() throws Exception {
        doNothing().when(citaService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/cita/1")).andExpect(status().isOk());
        verify(citaService, times(1)).eliminar(1L);
    }
}
