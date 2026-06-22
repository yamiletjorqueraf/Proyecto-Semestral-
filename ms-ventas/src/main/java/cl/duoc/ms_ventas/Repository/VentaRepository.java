package cl.duoc.ms_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_ventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{

}
