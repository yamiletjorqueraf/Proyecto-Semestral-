package cl.duoc.ms_personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ms_personal.model.Personal;

public interface PersonalRepository extends JpaRepository <Personal, Long> {

}
