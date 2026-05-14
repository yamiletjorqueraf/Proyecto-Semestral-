package com.veterinaria.personal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.personal_service.model.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal,Long>{

}
