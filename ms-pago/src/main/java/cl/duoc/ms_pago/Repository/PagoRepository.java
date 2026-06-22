package cl.duoc.ms_pago.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_pago.Model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{

}
