package com.edutech.curse_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    private String nombreCurso;
    private String descripcion; // corregido
    private String modalidad; // Presencial, online, hibrida
    private int cupoMaximo;

    private Long idProfesor;
    private Long idMateria;

}

