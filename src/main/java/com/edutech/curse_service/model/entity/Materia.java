package com.edutech.curse_service.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Materia {
    
    private Long idMateria;
    private String nombreMateria;
}
