package cl.duoc.ms_usuario.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cl.duoc.ms_usuario.Assamblers.UsuarioModelAssembler;
import cl.duoc.ms_usuario.Controller.UsuarioController;
import cl.duoc.ms_usuario.Model.Usuario;
import cl.duoc.ms_usuario.Service.UsuarioService;

import cl.duoc.ms_usuario.dto.UsuarioDTO;
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
public class UsuarioControllerTest {

    private MockMvc mockMvc;
 
    @Mock
    private UsuarioService usuarioService;
 
    @Mock
    private UsuarioModelAssembler assembler;
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    private Usuario usuario;
    private UsuarioDTO usuarioDto;
 
    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Juan", "Pérez", "Admin", "juan@mail.com", 912345678L);
        usuarioDto = new UsuarioDTO(null, "Juan", "Pérez", "Admin", "juan@mail.com", 912345678L);
 
        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(UsuarioDTO.fromModel(usuario)));
 
        UsuarioController controller = new UsuarioController(usuarioService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
 
    @Test
    public void testListarUsuarios() throws Exception {
        when(usuarioService.listar()).thenReturn(List.of(usuario));
        mockMvc.perform(get("/api/v1/usuario"))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testBuscarPorId() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        mockMvc.perform(get("/api/v1/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"));
    }
 
    @Test
    public void testCrearUsuario() throws Exception {
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(post("/api/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
 
    @Test
    public void testActualizar() throws Exception {
        when(usuarioService.actualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(put("/api/v1/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
 
    @Test
    public void testEliminar() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);
        mockMvc.perform(delete("/api/v1/usuario/1"))
                .andExpect(status().isOk());
        verify(usuarioService, times(1)).eliminar(1L);
    }

}
