package cl.duoc.ms_dueno.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_dueno.Model.Dueno;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long>{

}
