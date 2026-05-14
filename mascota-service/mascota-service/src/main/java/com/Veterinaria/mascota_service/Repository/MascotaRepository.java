package com.Veterinaria.mascota_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Veterinaria.mascota_service.Model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long>{

}
