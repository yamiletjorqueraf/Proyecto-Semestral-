package cl.duoc.ms_cita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_cita.model.Cita;
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long>{

}
