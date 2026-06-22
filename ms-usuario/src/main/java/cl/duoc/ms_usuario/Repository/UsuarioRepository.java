package cl.duoc.ms_usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_usuario.Model.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


}
