package com.edutech.curse_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "cursos",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombreCurso", "idProfesor", "idMateria"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @Column(nullable = false)
    private String nombreCurso;
    private String descripcion; // corregido
    private String modalidad; // Presencial, online, hibrida
    private int cupoMaximo;

    @Column(nullable = false)
    private Long idProfesor;

    @Column(nullable = false)
    private Long idMateria;

}

