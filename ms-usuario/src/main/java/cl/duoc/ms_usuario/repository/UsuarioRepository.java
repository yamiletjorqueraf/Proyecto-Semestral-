package cl.duoc.ms_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_usuario.model.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


}
