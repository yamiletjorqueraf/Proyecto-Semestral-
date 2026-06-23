package cl.duoc.ms_usuario.service;

import cl.duoc.ms_usuario.model.Usuario;
import cl.duoc.ms_usuario.repository.UsuarioRepository;
import cl.duoc.ms_usuario.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
 
    @InjectMocks
    private UsuarioService usuarioService;
 
    private Usuario usuario;
 
    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Juan", "Pérez", "Admin", "juan@mail.com", 912345678L);
    }
 
    @Test
    void testListar() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        List<Usuario> resultado = usuarioService.listar();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }
 
    @Test
    void testGuardar() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario resultado = usuarioService.guardar(usuario);
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
 
    @Test
    void testFindByIdExitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioService.findById(1L);
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdUsuario());
    }
 
    @Test
    void testFindByIdNoEncontrado() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Usuario> resultado = usuarioService.findById(99L);
        assertFalse(resultado.isPresent());
    }
 
    @Test
    void testExistePorId() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        assertTrue(usuarioService.existePorId(1L));
    }
 
    @Test
    void testActualizar() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario resultado = usuarioService.actualizar(1L, usuario);
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
 
    @Test
    void testActualizarNoEncontrado() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> usuarioService.actualizar(99L, usuario));
    }
 
    @Test
    void testEliminar() {
        doNothing().when(usuarioRepository).deleteById(1L);
        assertDoesNotThrow(() -> usuarioService.eliminar(1L));
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

}
