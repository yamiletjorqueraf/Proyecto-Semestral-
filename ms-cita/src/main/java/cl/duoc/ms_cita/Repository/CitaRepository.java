package cl.duoc.ms_cita.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_cita.Model.Cita;
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long>{

}
