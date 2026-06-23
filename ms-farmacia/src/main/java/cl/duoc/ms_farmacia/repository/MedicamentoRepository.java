package cl.duoc.ms_farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ms_farmacia.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long>{

}
