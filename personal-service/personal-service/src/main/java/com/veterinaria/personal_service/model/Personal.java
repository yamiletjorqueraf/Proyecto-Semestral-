package com.veterinaria.personal_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personal {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_personal;
    
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String correo;
}
