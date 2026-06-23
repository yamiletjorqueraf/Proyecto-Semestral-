package cl.duoc.ms_hospitalizacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_hospitalizacion.model.Hospitalizacion;

@Repository
public interface HospitalizacionRepository extends JpaRepository<Hospitalizacion, Long>{

}
