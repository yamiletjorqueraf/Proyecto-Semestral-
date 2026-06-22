package cl.duoc.ms_ventas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_ventas.Model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{

}
