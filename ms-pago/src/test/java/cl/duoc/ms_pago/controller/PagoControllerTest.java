package cl.duoc.ms_pago.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.ms_pago.assamblers.PagoModelAssembler;
import cl.duoc.ms_pago.controller.PagoController;
import cl.duoc.ms_pago.dto.PagoDTO;
import cl.duoc.ms_pago.model.Pago;
import cl.duoc.ms_pago.service.PagoService;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PagoControllerTest {
     private MockMvc mockMvc;
 
    @Mock
    private PagoService pagoService;
 
    @Mock
    private PagoModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    private Pago pago;
    private PagoDTO pagoDto;
 
    @BeforeEach
    void setUp() {
        // Modelo que devuelve el service
        pago = new Pago(
                1L,    // idPago
                2L,    // idVenta
                3L,    // idDueno
                1000,  // valorNeto
                190,   // iva
                10,    // descuento
                1090,  // totalPagar
                "Tarjeta",    // medioPago
                "Completado", // estado
                new Date()    // fecha
        );
 
        // DTO que llega en el request
        pagoDto = new PagoDTO(
                null,      // idPago (READ_ONLY)
                2L,        // idVenta
                3L,        // idDueno
                1000,      // valorNeto
                null,      // iva (READ_ONLY)
                10,        // descuento
                null,      // totalPagar (READ_ONLY)
                "Tarjeta", // medioPago
                "Completado", // estado
                null       // fecha (READ_ONLY)
        );
 
        // El assembler devuelve un EntityModel con el DTO
        when(assembler.toModel(any(Pago.class)))
                .thenReturn(EntityModel.of(PagoDTO.fromModel(pago)));
 
        PagoController controller = new PagoController(pagoService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    // ─── LISTAR ───────────────────────────────────────────────
    @Test
    public void testListarPagos() throws Exception {
        when(pagoService.listar()).thenReturn(List.of(pago));
 
        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk());
    }
 
    // ─── OBTENER POR ID ───────────────────────────────────────
    @Test
    public void testObtenerPago() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(pago);
 
        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago").value(1L))
                .andExpect(jsonPath("$.idVenta").value(2L))
                .andExpect(jsonPath("$.medioPago").value("Tarjeta"))
                .andExpect(jsonPath("$.valorNeto").value(1000))
                .andExpect(jsonPath("$.descuento").value(10))
                .andExpect(jsonPath("$.totalPagar").value(1090))
                .andExpect(jsonPath("$.estado").value("Completado"));
    }
 
    // ─── CREAR ────────────────────────────────────────────────
    @Test
    public void testCrearPago() throws Exception {
        when(pagoService.guardar(any(Pago.class))).thenReturn(pago);
 
        mockMvc.perform(post("/api/v1/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago").value(1L))
                .andExpect(jsonPath("$.idVenta").value(2L))
                .andExpect(jsonPath("$.medioPago").value("Tarjeta"))
                .andExpect(jsonPath("$.valorNeto").value(1000))
                .andExpect(jsonPath("$.descuento").value(10))
                .andExpect(jsonPath("$.totalPagar").value(1090));
    }
 
    // ─── ACTUALIZAR ───────────────────────────────────────────
    @Test
    public void testActualizarPago() throws Exception {
        when(pagoService.actualizar(eq(1L), any(Pago.class))).thenReturn(pago);
 
        mockMvc.perform(put("/api/v1/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago").value(1L))
                .andExpect(jsonPath("$.idVenta").value(2L))
                .andExpect(jsonPath("$.medioPago").value("Tarjeta"))
                .andExpect(jsonPath("$.valorNeto").value(1000))
                .andExpect(jsonPath("$.descuento").value(10))
                .andExpect(jsonPath("$.totalPagar").value(1090));
    }
 
    // ─── ELIMINAR ─────────────────────────────────────────────
    @Test
    public void testEliminarPago() throws Exception {
        doNothing().when(pagoService).eliminar(1L);
 
        mockMvc.perform(delete("/api/v1/pagos/1"))
                .andExpect(status().isOk());
 
        verify(pagoService, times(1)).eliminar(1L);
    }


}
