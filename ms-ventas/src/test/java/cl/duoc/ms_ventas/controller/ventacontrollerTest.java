package cl.duoc.ms_ventas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cl.duoc.ms_ventas.assamblers.VentaModelAssembler;
import cl.duoc.ms_ventas.controller.VentaController;
import cl.duoc.ms_ventas.dto.VentaDTO;
import cl.duoc.ms_ventas.model.Venta;
import cl.duoc.ms_ventas.service.VentaService;

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
 
import java.util.Date;
import java.util.List;
 
@ExtendWith(MockitoExtension.class)
public class ventacontrollerTest {

    private MockMvc mockMvc;
 
    @Mock
    private VentaService ventaService;
 
    @Mock
    private VentaModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    private Venta venta;
    private VentaDTO ventaDto;
 
    @BeforeEach
    void setUp() {
        venta = new Venta(
                1L, 2L, 3L,
                "Consulta",
                "Revisión general del animal",
                15000,
                "Completado",
                new Date()
        );
 
        ventaDto = new VentaDTO(
                null, 2L, 3L,
                "Consulta",
                "Revisión general del animal",
                15000,
                "Completado",
                null
        );
 
        when(assembler.toModel(any(Venta.class)))
                .thenReturn(EntityModel.of(VentaDTO.fromModel(venta)));
 
        VentaController controller = new VentaController(ventaService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarVentas() throws Exception {
        when(ventaService.listar()).thenReturn(List.of(venta));
        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testObtenerVenta() throws Exception {
        when(ventaService.obtenerPorId(1L)).thenReturn(venta);
        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(1))
                .andExpect(jsonPath("$.idDueno").value(2))
                .andExpect(jsonPath("$.idMascota").value(3))
                .andExpect(jsonPath("$.tipoServicio").value("Consulta"))
                .andExpect(jsonPath("$.monto").value(15000))
                .andExpect(jsonPath("$.estado").value("Completado"));
    }
 
    @Test
    public void testCrearVenta() throws Exception {
        when(ventaService.guardar(any(Venta.class))).thenReturn(venta);
        mockMvc.perform(post("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ventaDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVenta").value(1))
                .andExpect(jsonPath("$.tipoServicio").value("Consulta"))
                .andExpect(jsonPath("$.monto").value(15000));
    }
 
    @Test
    public void testActualizarVenta() throws Exception {
        when(ventaService.actualizar(eq(1L), any(Venta.class))).thenReturn(venta);
        mockMvc.perform(put("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ventaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(1))
                .andExpect(jsonPath("$.tipoServicio").value("Consulta"));
    }
 
    @Test
    public void testEliminarVenta() throws Exception {
        doNothing().when(ventaService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isOk());
        verify(ventaService, times(1)).eliminar(1L);
    }

}
