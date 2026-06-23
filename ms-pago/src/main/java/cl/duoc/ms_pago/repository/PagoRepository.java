package cl.duoc.ms_pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_pago.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{

}
