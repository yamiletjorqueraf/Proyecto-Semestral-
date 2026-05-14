package com.Veterinaria.mascota_service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Veterinaria.mascota_service.Model.Mascota;
import com.Veterinaria.mascota_service.Repository.MascotaRepository;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public boolean existePorId(Long id_mascota) {
        return mascotaRepository.existsById(id_mascota);
    }

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

}
