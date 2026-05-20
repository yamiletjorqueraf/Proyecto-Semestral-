package cl.duoc.ms_mascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_mascota.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

}
