package com.veterinaria.personal_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.personal_service.model.Personal;
import com.veterinaria.personal_service.repository.PersonalRepository;

@Service
public class PersonalService {
private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public Personal guardar(Personal personal) {
        return personalRepository.save(personal);
    }

    public boolean existePorId(Long id_personal) {
        return personalRepository.existsById(id_personal);
    }

    public List<Personal> listar() {
        return personalRepository.findAll();
    }
}
