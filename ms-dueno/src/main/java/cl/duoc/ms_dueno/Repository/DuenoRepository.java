package cl.duoc.ms_dueno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_dueno.model.Dueno;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long>{

}
