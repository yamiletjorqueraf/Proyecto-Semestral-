package cl.duoc.ms_resultados_examenes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;

@Repository
public interface ResultadoExamenRepository extends JpaRepository<ResultadoExamen, Long>{
    List<ResultadoExamen> findByIdMascota(Long idMascota);
}
