package cl.duoc.ms_mascota.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 
import cl.duoc.ms_mascota.assamblers.MascotaModelAssembler;
import cl.duoc.ms_mascota.dto.MascotaDTO;
import cl.duoc.ms_mascota.model.Mascota;
import cl.duoc.ms_mascota.service.MascotaService;
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
public class MascotaControllerTest {
 
    private MockMvc mockMvc;
 
    @Mock
    private MascotaService mascotaService;
 
    @Mock
    private MascotaModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Mascota mascota;
    private MascotaDTO mascotaDto;
 
    @BeforeEach
    void setUp() {
        mascota = new Mascota(1L, "Firulais", "Perro", "Labrador", 3, 2L);
        mascotaDto = new MascotaDTO(null, "Firulais", "Perro", "Labrador", 3, 2L);
 
        when(assembler.toModel(any(Mascota.class)))
                .thenReturn(EntityModel.of(MascotaDTO.fromModel(mascota)));
 
        MascotaController controller = new MascotaController(mascotaService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarMascotas() throws Exception {
        when(mascotaService.listar()).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/v1/mascota")).andExpect(status().isOk());
    }
 
    @Test
    public void testBuscarPorId() throws Exception {
        when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));
        mockMvc.perform(get("/api/v1/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMascota").value(1))
                .andExpect(jsonPath("$.nombre").value("Firulais"));
    }
 
    @Test
    public void testCrearMascota() throws Exception {
        when(mascotaService.guardar(any(Mascota.class))).thenReturn(mascota);
        mockMvc.perform(post("/api/v1/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascotaDto)))
                .andExpect(status().isCreated());
    }
 
    @Test
    public void testActualizar() throws Exception {
        when(mascotaService.actualizar(eq(1L), any(Mascota.class))).thenReturn(mascota);
        mockMvc.perform(put("/api/v1/mascota/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascotaDto)))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testEliminar() throws Exception {
        doNothing().when(mascotaService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/mascota/1")).andExpect(status().isOk());
        verify(mascotaService, times(1)).eliminar(1L);
    }
}
